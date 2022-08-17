package com.licon.redis.core.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/17 16:12
 */
@Configuration
@RequiredArgsConstructor
public class AliSmsConfig {
	private final AppProperties appProperties;

	@Bean
	public Client createSmsClient() throws Exception {
		Config config = new Config()
				// 您的AccessKey ID
				.setAccessKeyId(appProperties.getSmsProvider().getAli().getApiKey())
				// 您的AccessKey Secret
				.setAccessKeySecret(appProperties.getSmsProvider().getAli().getApiSecret())
				//访问的域名
				.setEndpoint(appProperties.getSmsProvider().getAli().getHostUrl());
		return new Client(config);
	}
}
