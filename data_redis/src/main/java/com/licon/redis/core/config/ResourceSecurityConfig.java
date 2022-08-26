package com.licon.redis.core.config;

import com.licon.redis.core.security.dsl.ClientErrorLoggingConfigurer;
import com.licon.redis.core.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity(debug = true)
@Import(SecurityProblemSupport.class)
@RequiredArgsConstructor
@Order(99)
public class ResourceSecurityConfig {

    private final SecurityProblemSupport securityProblemSupport;

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.requestMatchers(request -> request.mvcMatchers("/api/**","/admin/**","/authorize"))
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(exception->{
                exception.authenticationEntryPoint(securityProblemSupport)
                        .accessDeniedHandler(securityProblemSupport);
            })
            .authorizeRequests(authorize->
                authorize
                        .antMatchers(HttpMethod.OPTIONS,"/**")
                        .permitAll()
                        .antMatchers("/admin/**")
                        .hasAuthority("ADMIN")
                        .antMatchers("/authorize/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .cors().disable();

        return http.build();
    }

    @Bean
    public ClientErrorLoggingConfigurer clientErrorLogging() {
        return new ClientErrorLoggingConfigurer(new ArrayList<>());
    }
}
