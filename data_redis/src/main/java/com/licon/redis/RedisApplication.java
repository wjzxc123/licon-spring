package com.licon.redis;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.licon.redis.core.repository.persistence"},repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EnableRedisRepositories(basePackages = {"com.licon.redis.core.repository.cache"})
@EnableElasticsearchRepositories(basePackages = {"com.licon.redis.core.repository.search"})
public class RedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}
}