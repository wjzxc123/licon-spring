package com.licon.redis.core.api.controller;

import java.util.Locale;

import com.licon.redis.core.api.dto.UserDto;
import com.licon.redis.core.api.service.UserService;
import com.licon.redis.core.converter.mapper.UserConverter;
import com.licon.redis.core.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/authorize")
@RestController
@RequiredArgsConstructor
public class AuthorityResource {
    private final UserService userService;
    private final UserConverter userConverter;

    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto register(@Valid @RequestBody UserDto userDto, Locale locale){
        val user = userConverter.userDtoToUser(userDto)
                .withUsingMfa(true)
                .withEnabled(true)
                .withAccountNonExpired(true)
                .withAccountNonLocked(true)
                .withCredentialsNonExpired(true);
        return userConverter.userToUserDto(userService.register(user));
    }

    @GetMapping("/getAuth")
    public SecurityContext getAuth(){
        return SecurityContextHolder.getContext();
    }

}
