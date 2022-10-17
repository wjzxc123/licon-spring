package com.licon.redis.core.config;

import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.passay.MessageResolver;
import org.passay.spring.SpringMessageResolver;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfigutration implements WebMvcConfigurer {
    private final MessageSource messageSource;

    @Bean
    @Override
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }


    @Bean
    public MessageResolver messageResolver() {
        return new SpringMessageResolver(messageSource);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModules(
                new ProblemModule(),
                new ConstraintViolationProblemModule());
    }


    @Bean
    public CorsFilter corsFilter() {
        //创建CorsConfiguration对象后添加配置
        CorsConfiguration config = new CorsConfiguration();
        //设置放行哪些原始域，这里直接设置为所有
        config.addAllowedOriginPattern("*");
        //你可以单独设置放行哪些原始域 config.addAllowedOrigin("http://localhost:2222");
        //放行哪些原始请求头部信息
        config.addAllowedHeader("*");
        //放行哪些请求方式，*代表所有
        config.addAllowedMethod("*");
        //是否允许发送Cookie，必须要开启，因为我们的JSESSIONID需要在Cookie中携带
        config.setAllowCredentials(true);
        config.setExposedHeaders(Collections.singletonList("X-Authenticate"));
        //映射路径
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        //返回CorsFilter
        return new CorsFilter(corsConfigurationSource);
    }

    /**
     * controller 常常仅有返回页面的空方法，可以直接在这里做页面映射
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/login").setViewName("login1");
    }
}
