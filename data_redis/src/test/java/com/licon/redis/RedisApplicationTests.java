package com.licon.redis;


import java.util.Optional;
import java.util.UUID;

import com.licon.redis.core.entity.Authority;
import com.licon.redis.core.entity.UserAuthority;
import com.licon.redis.core.repository.cache.UserCacheRepository;
import com.licon.redis.core.entity.User;
import com.licon.redis.core.repository.persistence.AuthorityRepository;
import com.licon.redis.core.repository.persistence.UserAuthorityRepository;
import com.licon.redis.core.repository.persistence.UserRepository;
import com.licon.redis.core.repository.search.UserSearchRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revisions;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
//@ActiveProfiles("com")
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

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private UserAuthorityRepository userAuthorityRepository;

	@Test
	public void testAdd(){
		redisTemplateString.opsForValue().set("name","wy");
	}

	@Test
	public void testMysqlData(){
		User user = new User(System.currentTimeMillis(), "wjzxc123", "123456", 1,false,false,false,false);
		userRepository.save(user);

		//Authority authority = new Authority(System.currentTimeMillis(),"ADMIN","管理员");
		//authorityRepository.save(authority);

		//userAuthorityRepository.save(new UserAuthority(System.currentTimeMillis(),user.getId(),authority.getId(),true));
	}

	@Test
	public void testRedisData(){
		User user = new User(123L, "wjzxc123", "123456", 1,false,false,false,false);
		User save = userCacheRepository.save(user);
		System.out.println(save);
	}

	@Test
	public void saveUser(){
		User user = new User(333L, "wjzxc123", "123456", 1,false,false,false,false);
		redisTemplate.opsForHash().put("test","123",user);
	}

	@Test
	public void saveUserToEs(){
		User user = new User(333L, "wjzxc123", "123456", 1,false,false,false,false);
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
		System.out.println(passwordEncoder.encode("123456"));
	}

	@Test
	public void productSalt(){
		System.out.println(KeyGenerators.string().generateKey());
	}

	@Test
	public void findUser(){
		Optional<User> wjzxc123 = userRepository.findAllByUsername("wjzxc123");
		System.out.println(wjzxc123.get());
	}
}
