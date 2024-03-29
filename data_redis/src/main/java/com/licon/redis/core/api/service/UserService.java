package com.licon.redis.core.api.service;

import java.util.Optional;

import com.licon.redis.core.api.dto.Auth;
import com.licon.redis.core.api.dto.UserDto;
import com.licon.redis.core.api.exception.RegisterProblem;
import com.licon.redis.core.entity.User;
import com.licon.redis.core.repository.persistence.RoleRepository;
import com.licon.redis.core.repository.persistence.UserRepository;
import com.licon.redis.core.type.MfaType;
import com.licon.redis.core.util.CodeTotp;
import com.licon.redis.core.util.Constants;
import com.licon.redis.core.util.JwtUtil;
import com.licon.redis.core.util.SmsTotp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.elasticsearch.core.Set;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = RuntimeException.class)
public class UserService {
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final SmsTotp smsTotp;

    private final CodeTotp codeTotp;

    public User register(User user){
       return roleRepository.findOptionalByRoleCode(Constants.ROLE_USER)
                .map(role->{
                    val userToSave = user.withRoles(Set.of(role))
                            .withPassword(passwordEncoder.encode(user.getPassword()))
                            .withUsingMfa(true)
                            .withSmsMfaKey(smsTotp.generateStringKey())
                            .withCodeMfaKey(codeTotp.generateStringKey())
                            .withMfaType(MfaType.SMS);
                    return userRepository.save(userToSave);
                }).orElseThrow(RegisterProblem::new);

    }

    public Auth login(UserDetails userDetails){
        return  new Auth(jwtUtil.createAccessToken(userDetails), jwtUtil.createRefreshToken(userDetails));
    }

    public Auth loginTotp(User user){
        val toSave = user.withSmsMfaKey(smsTotp.generateStringKey());
        val saved = save(toSave);
        return login(saved);
    }

    /**
     * 根据用户名和密码查找用户
     * @param username
     * @param plainPassword
     * @return
     */
    public Optional<User> findOptionalByUsernameAndPassword(String username,String plainPassword){
        return userRepository.findOptionalByUsername(username)
                .filter(user->passwordEncoder.matches(plainPassword,user.getPassword()));
    }

    public Optional<User> findOptionalByUsername(String username){
        return userRepository.findOptionalByUsername(username);
    }
    /**
     * 升级密码加密方式
     * @param user
     * @param rowPasswotd
     */
    public void upgradePasswordEncodingIfNeed(User user,String rowPasswotd){
        if (passwordEncoder.upgradeEncoding(user.getPassword())){
            userRepository.save(user.withPassword(passwordEncoder.encode(rowPasswotd)));
        }
    }

    /**
     * 创建totp
     * @param user
     * @return
     */
    public Optional<String> createSmsTotp(User user){
        return smsTotp.createTotp(user.getSmsMfaKey());
    }

    public boolean isUsernameExisted(String username){
        return userRepository.countByUsername(username) > 0;
    }

    public boolean isEmailExisted(String email){
        return userRepository.countByEmail(email) > 0;
    }

    public boolean isMobileExisted(String mobile){
        return userRepository.countByMobile(mobile) > 0;
    }

    public User save(User user){
        return userRepository.save(user);
    }

}
