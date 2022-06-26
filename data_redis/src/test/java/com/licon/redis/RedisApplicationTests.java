package com.licon.redis;

import com.licon.redis.core.repository.cache.UserCacheRepository;
import com.licon.redis.core.entity.User;
import com.licon.redis.core.repository.persistence.UserRepository;
import com.licon.redis.core.repository.search.UserSearchRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revisions;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("hom")
class RedisApplicationTests {


	@Autowired
	UserRepository userRepository;

	@Autowired
	UserCacheRepository userCacheRepository;

	@Autowired
	UserSearchRepository userSearchRepository;

	@Autowired
	private StringRedisTemplate redisTemplateString;

	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testAdd(){
		redisTemplateString.opsForValue().set("name","wy");
	}

	@Test
	public void testMysqlData(){
		User user = new User(123L, "wjzxc123", "123456", 1);
		User save = userRepository.save(user);
		System.out.println(save);
	}

	@Test
	public void testRedisData(){
		User user = new User(123L, "wjzxc123", "123456", 1);
		User save = userCacheRepository.save(user);
		System.out.println(save);
	}

	@Test
	public void saveUser(){
		User user = new User(333L, "wjzxc123", "123456", 1);
		redisTemplate.opsForHash().put("test","123",user);
	}

	@Test
	public void saveUserToEs(){
		User user = new User(333L, "wjzxc123", "123456", 1);
		User save = userSearchRepository.save(user);
		System.out.println(save);
	}

	@Test
	public void findEnvers(){
		Revisions<Integer, User> revisions = userRepository.findRevisions(123L);
		revisions.forEach(x-> System.out.println(x.getMetadata()));
	}

	@Test
	public void testPassword(){
		System.out.println(passwordEncoder.encode("root"));
	}
}
