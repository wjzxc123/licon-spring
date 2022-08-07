package com.licon.redis.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/23 17:30
 */
@Configuration
@EnableSpringHttpSession
public class SessionConfig extends AbstractHttpSessionApplicationInitializer {

	@Bean
	public RedisSessionRepository sessionRepository(RedisTemplate<String,Object> redisTemplate) {
		RedisSessionRepository redisSessionRepository = new RedisSessionRepository(redisTemplate);
		redisSessionRepository.setRedisKeyNamespace("licon");
		return redisSessionRepository;
	}
}
