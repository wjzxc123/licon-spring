package com.licon.redis.core.security.dsl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.List;

@RequiredArgsConstructor
public class ClientErrorLoggingConfigurer extends AbstractHttpConfigurer<ClientErrorLoggingConfigurer, HttpSecurity> {

    private final List<HttpStatus> errorCodes;

    @Override
    public void init(HttpSecurity http) {
        // initialization code
    }

    @Override
    public void configure(HttpSecurity http) {
        // 配置 Filter，让它的位置在 FilterSecurityInterceptor 之后
        http.addFilterAfter(new ClientErrorLoggingFilter(errorCodes), FilterSecurityInterceptor.class);
    }

}