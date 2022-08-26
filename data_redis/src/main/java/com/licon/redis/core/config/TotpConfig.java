package com.licon.redis.core.config;



import com.licon.redis.core.util.CodeTotp;
import com.licon.redis.core.util.CreateCodeInfo;
import com.licon.redis.core.util.SmsTotp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/26 9:27
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class TotpConfig {
	private final AppProperties appProperties;

	@Bean("smsTotpInfo")
	CreateCodeInfo createSmsTotpInfo(){
		return CreateCodeInfo.builder()
				.setTimeStep(appProperties.getTotpProvider().getSmsTotp().getTempStep())
				.setOtpLength(appProperties.getTotpProvider().getSmsTotp().getOtpLength())
				.setAlgorithm(appProperties.getTotpProvider().getSmsTotp().getAlgorithm())
				.build();
	}

	@Bean("codeTotpInfo")
	CreateCodeInfo createCodeTotpInfo(){
		return CreateCodeInfo.builder()
				.setTimeStep(appProperties.getTotpProvider().getCodeTotp().getTempStep())
				.setOtpLength(appProperties.getTotpProvider().getCodeTotp().getOtpLength())
				.setAlgorithm(appProperties.getTotpProvider().getCodeTotp().getAlgorithm())
				.build();
	}

	@Bean
	SmsTotp smsTotpService(@Qualifier("smsTotpInfo") CreateCodeInfo createCodeInfo){
		return new SmsTotp(createCodeInfo);
	}

	@Bean
	CodeTotp codeTotpService(@Qualifier("codeTotpInfo") CreateCodeInfo createCodeInfo){
		return new CodeTotp(createCodeInfo);
	}
}
