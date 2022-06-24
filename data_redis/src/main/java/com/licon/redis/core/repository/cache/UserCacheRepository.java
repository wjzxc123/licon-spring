package com.licon.redis.core.repository.cache;

import com.licon.redis.core.entity.User;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/21 16:09
 */
@Repository
public interface UserCacheRepository extends KeyValueRepository<User,Long> {
}
