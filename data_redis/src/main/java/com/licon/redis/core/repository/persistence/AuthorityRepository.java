package com.licon.redis.core.repository.persistence;

import java.util.List;
import java.util.stream.Stream;

import com.licon.redis.core.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/29 13:13
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long> {
}
