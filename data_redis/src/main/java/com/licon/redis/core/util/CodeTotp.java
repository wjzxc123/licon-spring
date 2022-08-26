package com.licon.redis.core.util;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/26 11:33
 */
public class CodeTotp extends AbstractTotpService{

	public CodeTotp(CreateCodeInfo createCodeInfo) {
		super(createCodeInfo);
	}
	@Override
	public Key stringToKey(String key) {
		return new SecretKeySpec(key.getBytes(),getCreateCodeInfo().getAlgorithm());
	}

	@Override
	public String keyToString(Key key) {
		return encodeBase32KeyToString(key);
	}
}
