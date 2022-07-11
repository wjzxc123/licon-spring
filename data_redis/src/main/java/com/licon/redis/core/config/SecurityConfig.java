package com.licon.redis.core.config;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.licon.redis.core.api.validation.PasswordConstraintValidator;
import com.licon.redis.core.security.authentication.LiconAuthenticationEntryPoint;
import com.licon.redis.core.security.authority.AdminVoter;
import com.licon.redis.core.security.authority.LiconAccessDecisionManager;
import com.licon.redis.core.security.authority.LiconSecurityMetadataSource;
import com.licon.redis.core.security.user.LiconUserDetailService;

import com.licon.redis.core.security.user.LiconUserDetailsPassword;
import lombok.RequiredArgsConstructor;
import org.passay.MessageResolver;
import org.passay.spring.SpringMessageResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/23 16:19
 */
@Configuration
@EnableWebSecurity(debug = true)
@Import(SecurityProblemSupport.class)
@RequiredArgsConstructor
public class SecurityConfig{
	private final LiconAuthenticationEntryPoint authenticationEntryPoint;

	private final LiconSecurityMetadataSource liconSecurityMetadataSource;

	private final SecurityProblemSupport securityProblemSupport;


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorize->
					authorize.antMatchers("/login",
													"/licon-login",
													"/favicon.ico",
													"/*.html",
													"/**/*.css",
													"/**/*.js",
													"/authority/**")
							.permitAll()
							.anyRequest()
							.authenticated()
							/*.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
									@Override
									public <O extends FilterSecurityInterceptor> O postProcess(O object) {
										object.setAccessDecisionManager(liconAccessDecisionManager(accessDecisionVoterList()));
										object.setSecurityMetadataSource(liconSecurityMetadataSource);
										return object;
									}
							})*/
			)
			.formLogin(formlogin->
					formlogin
							.loginPage("/login")
							.loginProcessingUrl("/licon-login")
							.permitAll()
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
							})
			).csrf().disable();
		http.exceptionHandling(exception->{
			exception.authenticationEntryPoint(securityProblemSupport)
					.accessDeniedHandler(securityProblemSupport);
		});
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(){
		return web->web.ignoring().mvcMatchers();
	}

	@Bean
	@Primary
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(LiconUserDetailService userDetailService, LiconUserDetailsPassword userDetailsPassword){
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailService);
		authenticationProvider.setHideUserNotFoundExceptions(false);
		authenticationProvider.setUserDetailsPasswordService(userDetailsPassword);
		return authenticationProvider;
	}


	public LiconAccessDecisionManager liconAccessDecisionManager(List<AccessDecisionVoter<?>> voters){
		return new LiconAccessDecisionManager(voters);
	}


	public List<AccessDecisionVoter<?>> accessDecisionVoterList(){
		List<AccessDecisionVoter<?>> voters = new ArrayList<>();
		voters.add(new AdminVoter());
		return voters;
	}
}
