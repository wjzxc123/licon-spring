package com.licon.redis.core.repository.persistence;

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

}
