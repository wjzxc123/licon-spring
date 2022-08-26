package com.licon.redis.core.config;

import java.time.Duration;
import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
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
    private SmsProvider smsProvider = new SmsProvider();

    @Getter
    @Setter
    private TotpProvider totpProvider = new TotpProvider();



    @Getter
    @Setter
    public static class Jwt{
        private String header = "Authorization";

        private String prefix = "Bearer ";

        private String key;

        private String refreshKey;

        @Min(6_000L)
        private long accessTokenExpireTime = 60*60*1_000L;

        @Max(7_200_000L)
        private long refreshTokenExpireTime = 2*60*60*1_000L;
    }

    @Setter
    @Getter
    public static class SmsProvider{
        private String name;

        private String apiUrl;

        private Ali ali = new Ali();

        @Getter
        @Setter
        public static class Ali{
            private String apiKey;

            private String apiSecret;

            private String hostUrl;

            private String templateCode;

            private String signName;
        }
    }


    @Setter
    @Getter
    public static class TotpProvider{
        private Duration cacheTime;

        private Totp smsTotp = new Totp();

        private Totp codeTotp = new Totp();

        @Getter
        @Setter
        public static class Totp{
            @Max(300)
            @Min(30)
            private long tempStep = 60;

            @Max(12)
            @Min(6)
            private int otpLength = 6;

            private String algorithm = TimeBasedOneTimePasswordGenerator.TOTP_ALGORITHM_HMAC_SHA256;
        }
    }
}
