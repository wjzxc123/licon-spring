package com.licon.redis.core.config;

import com.licon.redis.core.security.dsl.ClientErrorLoggingConfigurer;
import com.licon.redis.core.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@EnableWebSecurity(debug = true)
@Import(SecurityProblemSupport.class)
@RequiredArgsConstructor
public class ResourceSecurityConfig {

    private final SecurityProblemSupport securityProblemSupport;

    private final JwtFilter jwtFilter;

    private final ClientRegistrationRepository clientRegistrationRepository;
    @Bean
    @Order(99)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.requestMatchers(request -> request.antMatchers("/api/**","/admin/**","/authorize"))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
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
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            //自定义配置
            .apply(new ClientErrorLoggingConfigurer(Arrays.asList(HttpStatus.UNAUTHORIZED,HttpStatus.INTERNAL_SERVER_ERROR)))
            .and()
            .sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(exception->{
                exception.authenticationEntryPoint(securityProblemSupport)
                        .accessDeniedHandler(securityProblemSupport);
            });

        return http.build();
    }

//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(clientRegistrationRepository.findByRegistrationId("github"));
//    }

}
