package com.licon.redis.core.config;


import java.io.PrintWriter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
		http.authorizeRequests(authorize->{
			authorize.antMatchers("/login").permitAll().anyRequest().authenticated();
		})
		.formLogin(formLogin->{
			formLogin.loginPage("/login").permitAll()
			.successHandler((request, response, authentication) -> {
				response.setCharacterEncoding("utf-8");
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				PrintWriter writer = response.getWriter();
				writer.println("{\"code\":200,\"success\":true,\"authentication\":"+authentication.getPrincipal()+"}");
				writer.flush();
				writer.close();
			})
			.failureHandler((request, response, exception) -> {
				response.setCharacterEncoding("utf-8");
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				PrintWriter writer = response.getWriter();
				writer.println("{\"code\":500,\"success\":false,\"exception\":"+exception.getMessage()+"}");
				writer.flush();
				writer.close();
			});
		});
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
	AuthenticationProvider authenticationProvider(UserDetailsService userDetailService){
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailService);
		return authenticationProvider;
	}
}
