package com.licon.redis.core.api.controller;

import java.util.Locale;

import com.licon.redis.core.api.dto.*;
import com.licon.redis.core.api.exception.*;
import com.licon.redis.core.api.service.SmsService;
import com.licon.redis.core.api.service.UserCacheService;
import com.licon.redis.core.api.service.UserService;
import com.licon.redis.core.api.validation.group.Group;
import com.licon.redis.core.converter.UserConverter;
import com.licon.redis.core.entity.User;
import com.licon.redis.core.type.MfaType;
import com.licon.redis.core.util.Constants;
import com.licon.redis.core.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.context.MessageSource;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/authorize")
@RestController
@RequiredArgsConstructor
public class AuthorityResource {
    private final UserService userService;
    private final UserConverter userConverter;
    private final MessageSource messageSource;
    private final UserCacheService userCacheService;
    private final SmsService smsService;

    @GetMapping("/validation/username")
    public boolean validateUsername(@RequestParam String username){
        return userService.isUsernameExisted(username);
    }

    @GetMapping("/validation/email")
    public boolean validateEmail(@RequestParam String email){
        return userService.isEmailExisted(email);
    }

    @GetMapping("/validation/mobile")
    public boolean validateMobile(@RequestParam String mobile){
        return userService.isMobileExisted(mobile);
    }

    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto register(@Validated(Group.class) @RequestBody UserDto userDto, Locale locale){
        if (userService.isUsernameExisted(userDto.getUsername())){
            throw new DuplicateProblem(messageSource.getMessage(Constants.DUPLICATE_TITLE,null,locale),
                    messageSource.getMessage("Exception.duplicate.username",null,locale));
        }

        if (userService.isMobileExisted(userDto.getMobile())){
            throw new DuplicateProblem(messageSource.getMessage(Constants.DUPLICATE_TITLE,null,locale),
                    messageSource.getMessage("Exception.duplicate.mobile",null,locale));
        }

        if (userService.isEmailExisted(userDto.getEmail())){
            throw new DuplicateProblem(messageSource.getMessage(Constants.DUPLICATE_TITLE,null,locale),
                    messageSource.getMessage("Exception.duplicate.email",null,locale));
        }

        val user = userConverter.userDtoToUser(userDto)
                .withUsingMfa(true)
                .withEnabled(true)
                .withAccountNonExpired(true)
                .withAccountNonLocked(true)
                .withCredentialsNonExpired(true);
        return userConverter.userToUserDto(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated(Group.class) @RequestBody LoginDto loginDto){
        return userService.findOptionalByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword())
                .map(user -> {
                    userService.upgradePasswordEncodingIfNeed(user,loginDto.getPassword());
                    if (!user.isEnabled()){
                        throw new UserNotEnabledProblem();
                    }

                    if (!user.isAccountNonLocked()){
                        throw new UserAccountLockedProblem();
                    }

                    if (!user.isAccountNonExpired()){
                        throw new UserAccountExpiredProblem();
                    }

                    if (!user.isCredentialsNonExpired()){
                        throw new UserCredentialsExpiredProblem();
                    }

                    //不使用多因子认证直接登录
                    if (!user.isUsingMfa()){

                        return ResponseEntity.ok().body(Result.ok(userService.login(user)));
                    }

                    //使用多因子认证
                    val mfaId = userCacheService.cacheUser(user);
                    val mfaType = user.getMfaType();
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .header("X-Authenticate",mfaType.getHeader(),"realm="+mfaId)
                            //.body(Result.builder().code(401).msg("need authentication").build())
                            .build();
                }).orElseThrow(BadCredentialsProblem::new);
    }

    @GetMapping("/getAuth")
    public SecurityContext getAuth(){
        return SecurityContextHolder.getContext();
    }


	@PutMapping("/send")
	public void sendTotp(@Validated @RequestBody TotpDto totpDto){
        Pair<String, User> pair = userCacheService.retrieveUser(totpDto.getMfaId())
                .flatMap(user -> userService.createSmsTotp(user).map(totp -> Pair.of(totp, user)))
                .orElseThrow(InvalidTotpProblem::new);

        if (totpDto.getMfaType() == MfaType.SMS){
            log.debug("短信发送验证码，手机号:{},验证码：{}",pair.getSecond().getMobile(),pair.getFirst());
            smsService.send(pair.getSecond().getMobile(),pair.getFirst());
        }
    }

    @PostMapping("/totp")
    public ResponseEntity<?> verifyTotp(@Validated(Group.class) @RequestBody VerifyTotpDto verifyTotpDto){
        val auth = userCacheService.verifyCode(verifyTotpDto.getMfaId(), verifyTotpDto.getCode(),verifyTotpDto.getMfaType())
                .map(User::getUsername)
                .flatMap(userService::findOptionalByUsername)
                .map(userService::loginTotp)
                .orElseThrow(InvalidTotpProblem::new);
        return ResponseEntity.ok().body(Result.ok(auth));
    }
}
