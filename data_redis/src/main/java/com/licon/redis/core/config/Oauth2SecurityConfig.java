package com.licon.redis.core.config;

import lombok.RequiredArgsConstructor;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/10/14 16:57
 */
@Configuration
@EnableWebSecurity(debug = true)
@Import(SecurityProblemSupport.class)
@RequiredArgsConstructor
public class Oauth2SecurityConfig {
	@Bean
	@Order(101)
	public SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
		http.requestMatchers(request->request.antMatchers("/oauth2/**"))
				.authorizeRequests()
				.anyRequest()
				.authenticated()
				.and()
				.oauth2Login();
		return http.build();
	}
}
