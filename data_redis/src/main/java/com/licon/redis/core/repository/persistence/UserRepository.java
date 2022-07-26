package com.licon.redis.core.repository.persistence;

import java.util.Optional;

import com.licon.redis.core.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/20 17:44
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>, RevisionRepository<User,Long,Integer> {
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	Optional<User> findOptionalByUsername(String username);
}
