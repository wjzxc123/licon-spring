package com.licon.redis.core.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.stereotype.Component;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/11 16:12
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TotpUtil {
	private final static long TIME_STEP = 60*5L;
	private final static int OTP_STRENGTH = 6;
	private KeyGenerator keyGenerator;
	private TimeBasedOneTimePasswordGenerator totp;

	//java8 特性，初始化代码块，会自动加到构造函数最前面执行
	{

		try {
			totp = new TimeBasedOneTimePasswordGenerator(Duration.ofSeconds(TIME_STEP),OTP_STRENGTH,TimeBasedOneTimePasswordGenerator.TOTP_ALGORITHM_HMAC_SHA256);
			keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
			// SHA-1 and SHA-256 需要 64 字节 (512 位) 的 key; SHA512 需要 128 字节 (1024 位) 的 key
			keyGenerator.init(512);
		}catch (NoSuchAlgorithmException e) {
			log.error("没有找到算法 {}", e.getLocalizedMessage());
		}
	}

	/**
	 * 创建totp
	 * @param key
	 * @param instant
	 * @return
	 * @throws InvalidKeyException
	 */
	public String createTotp(final Key key, Instant instant) throws InvalidKeyException {
		val format = "%0"+OTP_STRENGTH+"d";
		return String.format(format,totp.generateOneTimePassword(key,instant));
	}

	/**
	 * 创建totp
	 * @param strkey
	 * @return
	 */
	public Optional<String> createTotp(final String strkey){
		try {
			return Optional.of(createTotp(decodeKeyFromString(strkey),Instant.now()));
		}catch (InvalidKeyException e) {
			return Optional.empty();
		}
	}

	/**
	 * 生成秘钥
	 * @return
	 */
	public Key generateKey(){
		return keyGenerator.generateKey();
	}

	/**
	 * 将秘钥转化为字符串
	 * @param key
	 * @return
	 */
	public String encodeKeyToString(Key key){
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	/**
	 * 自动生成秘钥并将秘钥转化为字符串
	 * @return
	 */
	public String encodeKeyToString(){
		return encodeKeyToString(generateKey());
	}

	/**
	 * 将字串秘钥转化为key对象
	 * @param strKey
	 * @return
	 */
	public Key decodeKeyFromString(String strKey){
		return new SecretKeySpec(Base64.getDecoder().decode(strKey),totp.getAlgorithm());
	}


}
