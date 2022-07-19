package com.licon.redis.core.api.controller;

import com.licon.redis.core.api.dto.UserDto;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/api")
@RestController
public class AuthorityResource {
    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto register(@Valid @RequestBody UserDto userDto){
        return userDto;
    }

    @GetMapping("/getAuth")
    public SecurityContext getAuth(){
        return SecurityContextHolder.getContext();
    }

}
