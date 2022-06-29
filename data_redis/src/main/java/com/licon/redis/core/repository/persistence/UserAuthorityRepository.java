package com.licon.redis.core.repository.persistence;


import com.licon.redis.core.entity.UserAuthority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/29 14:24
 */
@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority,Long> {
	/**
	 * 根据用户id查找
	 * @param userId
	 * @return
	 */
	Streamable<UserAuthority> findByUserId(Long userId);
}
