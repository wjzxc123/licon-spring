package com.licon.redis.core.api.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.licon.redis.core.entity.User;
import com.licon.redis.core.util.Constants;
import com.licon.redis.core.util.CryptoUtil;
import com.licon.redis.core.util.TotpUtil;
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
	private final TotpUtil totpUtil;

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
			userCache.put(mfaId,user,totpUtil.getTimeStep(), TimeUnit.SECONDS);
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
}
