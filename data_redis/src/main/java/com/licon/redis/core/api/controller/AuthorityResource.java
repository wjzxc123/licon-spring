package com.licon.redis.core.api.controller;

import com.licon.redis.core.api.dto.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RequestMapping("/authority")
@RestController
public class AuthorityResource {
    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody UserDto userDto){
        return userDto;
    }

}
