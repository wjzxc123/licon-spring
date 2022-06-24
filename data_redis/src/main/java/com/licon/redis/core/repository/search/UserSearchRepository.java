package com.licon.redis.core.repository.search;

import com.licon.redis.core.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/22 13:43
 */
@Repository
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {
}
