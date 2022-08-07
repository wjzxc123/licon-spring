package com.licon.redis.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * jwt配置
 * @author Licon
 */
@Validated
@Configuration
@ConfigurationProperties(prefix = AppProperties.APP_PREFIX)
public class AppProperties {
    public final static String APP_PREFIX = "com.licon";

    @Getter
    @Setter
    private Jwt jwt = new Jwt();

    @Getter
    @Setter
    public static class Jwt{
        private String header = "Authorization";

        private String prefix = "Bearer ";

        private String key;

        private String refreshKey;

        @Min(6_000L)
        private long accessTokenExpireTime = 60*60*1_000L;

        @Max(3_600_000L)
        private long refreshTokenExpireTime = 2*60*60*1_000L;
    }
}
