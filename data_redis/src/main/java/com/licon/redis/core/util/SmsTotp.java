package com.licon.redis.core.util;

import java.security.Key;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/26 11:31
 */

public class SmsTotp extends AbstractTotpService{

	public SmsTotp(CreateCodeInfo createCodeInfo) {
		super(createCodeInfo);
	}

	@Override
	public Key stringToKey(String key) {
		return decodeKeyFromStringBase64(key);
	}

	@Override
	public String keyToString(Key key) {
		return encodeBase64KeyToString(key);
	}
}
