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

	/**
	 * 查询用户名是否存在
	 * @param username
	 * @return
	 */
	long countByUsername(String username);

	/**
	 * 查询用户邮箱是否存在
	 * @param email
	 * @return
	 */
	long countByEmail(String email);

	/**
	 * 查询用户手机号是否存在
	 * @param mobile
	 * @return
	 */
	long countByMobile(String mobile);
}
