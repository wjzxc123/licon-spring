package com.licon.redis.core.api.controller;

import java.util.Locale;

import com.licon.redis.core.api.dto.UserDto;
import com.licon.redis.core.api.service.UserService;
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

    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto register(@Valid @RequestBody UserDto userDto, Locale locale){
        val user = new User()
                .withUsername(userDto.getUsername())
                .withPassword(userDto.getPassword())
                .withUsingMfa(true)
                .withEmail(userDto.getEmail())
                .withEnabled(true)
                .withAccountNonExpired(true)
                .withAccountNonLocked(true)
                .withCredentialsNonExpired(true);
        userService.register(user);
        return userDto;
    }

    @GetMapping("/getAuth")
    public SecurityContext getAuth(){
        return SecurityContextHolder.getContext();
    }

}
