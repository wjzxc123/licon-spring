package com.licon.redis.core.config;


import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.HashMap;

import org.elasticsearch.core.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/23 16:19
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig{

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().formLogin(formLogin->{
			formLogin.successHandler((request, response, authentication) -> {
				response.setCharacterEncoding("utf-8");
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				PrintWriter writer = response.getWriter();
				writer.println("{\"code\":200,\"success\":true,\"authentication\":"+authentication.getPrincipal()+"}");
				writer.flush();
				writer.close();
			});
		});
		http.csrf().disable();
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(){
		return web -> web.ignoring().antMatchers("/test");
	}

	@Bean
	@Primary
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(){
		UserDetails user = User.withUsername("licon")
				.password("{bcrypt}$2a$10$Ihjhb8To05YH9HYs4rhqaOtnprkxVfTdEX/9Hc8uNays9UrX.XWf2")
				.roles("admin")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
}
