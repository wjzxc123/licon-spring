package com.licon.redis.core.api.service;

import com.licon.redis.core.api.dto.Auth;
import com.licon.redis.core.api.dto.UserDto;
import com.licon.redis.core.api.exception.RegisterProblem;
import com.licon.redis.core.entity.User;
import com.licon.redis.core.repository.persistence.RoleRepository;
import com.licon.redis.core.repository.persistence.UserRepository;
import com.licon.redis.core.util.Constants;
import com.licon.redis.core.util.JwtUtil;
import com.licon.redis.core.util.TotpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.elasticsearch.core.Set;

import org.springframework.security.access.AccessDeniedException;
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

    private final TotpUtil totpUtil;

    public User register(User user){
       return roleRepository.findOptionalByRoleCode(Constants.ROLE_USER)
                .map(role->{
                    val userToSave = user.withRoles(Set.of(role))
                            .withPassword(passwordEncoder.encode(user.getPassword()))
                            .withUsingMfa(true)
                            .withMfaKey(totpUtil.encodeKeyToString());
                    return userRepository.save(userToSave);
                }).orElseThrow(RegisterProblem::new);

    }

    public Auth login(UserDto userDto){
        return userRepository.findOptionalByUsername(userDto.getUsername())
                .filter(user -> passwordEncoder.matches(userDto.getPassword(),user.getPassword()))
                .map(user -> new Auth(jwtUtil.createAccessToken(user), jwtUtil.createRefreshToken(user)))
                .orElseThrow(()->new AccessDeniedException("用户名密码错误"));
    }

}
