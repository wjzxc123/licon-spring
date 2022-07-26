package com.licon.redis.core.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Auth {
    private String token;

    private String refreshToken;
}
