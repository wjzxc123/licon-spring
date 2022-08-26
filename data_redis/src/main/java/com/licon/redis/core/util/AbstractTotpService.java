package com.licon.redis.core.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;
import lombok.val;
import org.apache.commons.codec.binary.Base32;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/26 11:35
 */
public abstract class AbstractTotpService implements TotpService{

	private final CreateCodeInfo createCodeInfo;

	public AbstractTotpService(CreateCodeInfo createCodeInfo) {
		this.createCodeInfo = createCodeInfo;
	}

	public CreateCodeInfo getCreateCodeInfo(){
		return this.createCodeInfo;
	}

	@Override
	public Optional<String> createTotp(final Key key, Instant instant) throws InvalidKeyException {
		val format = "%0"+createCodeInfo.getOtpLength()+"d";
		return Optional.of(String.format(format,createCodeInfo.getTotp().generateOneTimePassword(key,instant)));
	}

	/**
	 * 创建totp
	 * @param strkey
	 * @return
	 */
	@Override
	public Optional<String> createTotp(final String strkey){
		try {
			return createTotp(stringToKey(strkey),Instant.now());
		}catch (InvalidKeyException e) {
			return Optional.empty();
		}
	}

	/**
	 * 生成秘钥
	 * @return
	 */
	@Override
	public final Key generateKey(){
		return createCodeInfo.getKeyGenerator().generateKey();
	}

	/**
	 * 生成字串的秘钥
	 * @return
	 */
	@Override
	public final String generateStringKey() {
		return keyToString(generateKey());
	}

	public final String generateStringKey(Key key){
		return keyToString(key);
	}
	/**
	 * 将秘钥按Base64转化为字符串
	 * @param key
	 * @return
	 */
	public final String encodeBase64KeyToString(Key key){
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}


	/**
	 * 将秘钥按Base32转化为字符串
	 * @param key
	 * @return
	 */
	public final String encodeBase32KeyToString(Key key){
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	/**
	 * 自动生成秘钥并将秘钥按Base64转化为字符串
	 * @return
	 */
	public final String encodeBase64KeyToString(){
		return encodeBase64KeyToString(generateKey());
	}

	/**
	 * 自动生成秘钥并将秘钥按Base32转化为字符串
	 * @return
	 */
	public final String encodeBase32KeyToString(){
		return encodeBase32KeyToString(generateKey());
	}

	/**
	 * 将Base64加密的字串秘钥转化为key对象
	 * @param base64Key
	 * @return
	 */
	public final Key decodeKeyFromStringBase64(String base64Key){
		return new SecretKeySpec(Base64.getDecoder().decode(base64Key),createCodeInfo.getAlgorithm());
	}

	/**
	 * 将Base32加密的字串秘钥转化为key对象
	 * @param base32Key
	 * @return
	 */
	public final Key decodeKeyFromStringBase32(String base32Key){
		return new SecretKeySpec(new Base32().decode(base32Key),createCodeInfo.getAlgorithm());
	}

	/**
	 * 校验totp是否正确，需要按照编码规则，生成key对象 decodeKeyFromStringBase32/decodeKeyFromStringBase64
	 * @param key
	 * @param code
	 * @return
	 * @throws InvalidKeyException
	 */
	@Override
	public final boolean validateTotp(String key,String code) throws InvalidKeyException {
		return createTotp(stringToKey(key),Instant.now())
				.filter(curCode->curCode.equalsIgnoreCase(code))
				.isPresent();
	}


	public abstract Key stringToKey(String key);

	public abstract String keyToString(Key key);
}
