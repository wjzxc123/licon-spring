package com.licon.redis;


import java.util.Optional;

import com.licon.redis.core.entity.Authority;
import com.licon.redis.core.entity.Role;
import com.licon.redis.core.repository.cache.UserCacheRepository;
import com.licon.redis.core.entity.User;
import com.licon.redis.core.repository.persistence.AuthorityRepository;
import com.licon.redis.core.repository.persistence.UserRepository;
import com.licon.redis.core.repository.search.UserSearchRepository;
import org.elasticsearch.core.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revisions;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.keygen.KeyGenerators;
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
	private RedisTemplate<String,Object> redisTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Test
	public void testAdd(){
		redisTemplate.opsForValue().set("name","wy");
	}

	@Test
	public void testMysqlData(){
		User user = User.builder()
				.username("admin")
				.password("{noop}12345678")
				.sex(1)
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.enabled(true)
				.roles(Set.of(Role.builder()
						.roleCode("ADMIN")
						.roleName("管理员")
						.enable(true)
						.authorities(Set.of(Authority.builder()
								.authority("read")
								.authorityName("读")
								.enable(true)
								.build(),
								Authority.builder()
								.authority("write")
								.authorityName("写")
								.enable(true)
								.build())).build()))
				.build();
		userRepository.save(user);
	}

	@Test
	public void testRedisData(){
		User user = new User()
				.withId(123L)
				.withUsername("wjzxc123")
				.withPassword("12345678")
				.withSex(1);

		User save = userCacheRepository.save(user);
		System.out.println(save);
	}

	@Test
	public void saveUser(){
		User user = new User()
				.withId(333L)
				.withUsername("wjzxc1234")
				.withPassword("12345678")
				.withSex(1);
		redisTemplate.opsForHash().put("test","123",user);
	}

	@Test
	public void saveUserToEs(){
		User user = new User()
				.withId(1234L)
				.withUsername("wjzxc123")
				.withPassword("12345678")
				.withSex(1);
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
		Optional<User> wjzxc123 = userRepository.findOptionalByUsername("wjzxc123");
		System.out.println(wjzxc123.get());
	}
}
