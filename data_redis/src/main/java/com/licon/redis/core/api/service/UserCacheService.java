package com.licon.redis.core.api.service;

import java.security.InvalidKeyException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

import com.licon.redis.core.api.exception.InvalidTotpProblem;
import com.licon.redis.core.config.AppProperties;
import com.licon.redis.core.entity.User;
import com.licon.redis.core.type.MfaType;
import com.licon.redis.core.util.CodeTotp;
import com.licon.redis.core.util.Constants;
import com.licon.redis.core.util.CryptoUtil;
import com.licon.redis.core.util.SmsTotp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/16 15:35
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserCacheService {

	private final CryptoUtil cryptoUtil;
	private final RedissonClient redission;

	private final CodeTotp codeTotp;

	private final SmsTotp smsTotp;

	private final AppProperties appProperties;

	/**
	 * 使用redis缓存用户，并返回一个mfaId
	 * @param user
	 * @return
	 */
	public String cacheUser(User user){
		val mfaId = cryptoUtil.randomAlphanumeric(12);
		log.debug("{} 生成 mfaId:{}",user.getUsername(),mfaId);
		RMapCache<String, User> userCache = redission.getMapCache(Constants.CACHE_MFA);
		if (!userCache.containsKey(mfaId)){
			userCache.put(mfaId,user, appProperties.getTotpProvider().getCacheTime().getSeconds(), TimeUnit.SECONDS);
		}
		return mfaId;
	}

	public Optional<User> retrieveUser(String mfaId){
		log.debug("接收短信登陆，mfaId:{}",mfaId);
		RMapCache<String, User> userCache = redission.getMapCache(Constants.CACHE_MFA);
		if (userCache.containsKey(mfaId)){
			log.debug("获取到mfaId:{}",mfaId);
			return Optional.of(userCache.get(mfaId));
		}
		return Optional.empty();
	}


	/**
	 * 校验手机令牌
	 * @param mfaId
	 * @param code
	 * @return
	 */
	public Optional<User> verifyCode(String mfaId,String code, MfaType mfaType){
		return verifyTotp(mfaId,code,(user)->{
			try {
				if (mfaType == MfaType.SMS) return smsTotp.validateTotp(user.getCodeMfaKey(), code);
				if (mfaType == MfaType.CODE) return codeTotp.validateTotp(user.getCodeMfaKey(), code);
				return false;
			}catch (InvalidKeyException e) {
				log.debug("key is valid {}",e.getLocalizedMessage());
			}
			return false;
		});
	}

	/**
	 * 校验totp
	 * @param mfaId
	 * @param code
	 * @param function
	 * @return
	 */
	public Optional<User> verifyTotp(String mfaId,String code, Function<User,Boolean> function){
		log.debug("mfaId:{},验证码:{}",mfaId,code);
		RMapCache<String, User> userCache = redission.getMapCache(Constants.CACHE_MFA);
		if (!userCache.containsKey(mfaId) || userCache.get(mfaId) == null){
			return Optional.empty();
		}
		val user = userCache.get(mfaId);
		if (!function.apply(user)){
			return Optional.empty();
		}
		userCache.remove(mfaId);
		return Optional.of(user);
	}
}
