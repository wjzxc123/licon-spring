package com.licon.redis.core.api.controller;

import com.licon.redis.core.api.dto.Auth;
import com.licon.redis.core.api.dto.UserDto;
import com.licon.redis.core.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/authorize")
@RequiredArgsConstructor
public class AuthorizeController {

    private final UserService userService;

    @PostMapping("/token")
    public Auth obtainToken(@Valid @RequestBody UserDto userDto){
        return userService.login(userDto);
    }
}
