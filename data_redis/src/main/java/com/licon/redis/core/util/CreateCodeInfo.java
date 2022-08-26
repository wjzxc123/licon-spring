package com.licon.redis.core.util;

import java.security.NoSuchAlgorithmException;
import java.time.Duration;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.validation.constraints.NotNull;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/26 9:24
 */
@Getter
@Slf4j
public class CreateCodeInfo {
	private final long timeStep;
	private final int otpLength;
	private final String algorithm;
	private KeyGenerator keyGenerator;
	private TimeBasedOneTimePasswordGenerator totp;

	public CreateCodeInfo(@NotNull long timeStep, @NotNull int optLength, @NotNull String algorithm) {
		this.timeStep = timeStep;
		this.otpLength = optLength;
		this.algorithm = algorithm;
		try {
			totp = new TimeBasedOneTimePasswordGenerator(Duration.ofSeconds(timeStep),optLength,algorithm);
			keyGenerator = KeyGenerator.getInstance(algorithm);
			final int macLengthInBytes = Mac.getInstance(algorithm).getMacLength();
			keyGenerator.init(macLengthInBytes*8);
		}catch (NoSuchAlgorithmException e) {
			log.error("没有找到算法 {}", e.getLocalizedMessage());
		}
	}
	public static Builder builder(){
		return new Builder();
	}
	@Getter
	@NoArgsConstructor
	public static class Builder{
		private long timeStep;
		private  int otpLength;
		private String algorithm;

		public Builder setTimeStep(long timeStep) {
			this.timeStep = timeStep;
			return this;
		}

		public Builder setOtpLength(int optLength) {
			this.otpLength = optLength;
			return this;
		}

		public Builder setAlgorithm(String algorithm) {
			this.algorithm = algorithm;
			return this;
		}

		public CreateCodeInfo build(){
			return new CreateCodeInfo(timeStep, otpLength, algorithm);
		}
	}
}
