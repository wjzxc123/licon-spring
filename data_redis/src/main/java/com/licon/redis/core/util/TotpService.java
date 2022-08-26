package com.licon.redis.core.util;


import java.security.InvalidKeyException;
import java.security.Key;
import java.time.Instant;
import java.util.Optional;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/26 10:27
 */
public interface TotpService {
	/**
	 * 创建totp根据指定时间
	 * @param key
	 * @param instant
	 * @return
	 * @throws InvalidKeyException
	 */
	Optional<String> createTotp(final Key key, Instant instant) throws InvalidKeyException;

	/**
	 * 创建当前时间的totp
	 * @param strkey
	 * @return
	 */
	Optional<String> createTotp(final String strkey);

	/**
	 * 生成一个totp加密key对象
	 * @return
	 */
	Key generateKey();

	/***
	 * 生成一个totp加密key编码后的字串
	 * @return
	 */
	String generateStringKey();

	/**
	 * 校验totp
	 * @param key
	 * @param code
	 * @return
	 * @throws InvalidKeyException
	 */
	boolean validateTotp(String key,String code) throws InvalidKeyException;
}
