package com.licon.redis.core.api.service;

import com.licon.redis.core.api.dto.Auth;
import com.licon.redis.core.api.dto.UserDto;
import com.licon.redis.core.repository.persistence.UserRepository;
import com.licon.redis.core.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Auth login(UserDto userDto){
        return userRepository.findOptionalByUsername(userDto.getUsername())
                .filter(user -> passwordEncoder.matches(userDto.getPassword(),user.getPassword()))
                .map(user -> new Auth(jwtUtil.createAccessToken(user), jwtUtil.createRefreshToken(user)))
                .orElseThrow(()->new AccessDeniedException("用户名密码错误"));
    }

}
