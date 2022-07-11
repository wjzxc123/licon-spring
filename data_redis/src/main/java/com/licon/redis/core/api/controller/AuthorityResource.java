package com.licon.redis.core.api.controller;

import com.licon.redis.core.api.dto.UserDto;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/authority")
@RestController
public class AuthorityResource {
    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody UserDto userDto){
        return userDto;
    }

    @GetMapping("/getAuth")
    public SecurityContext getAuth(){
        return SecurityContextHolder.getContext();
    }

}
