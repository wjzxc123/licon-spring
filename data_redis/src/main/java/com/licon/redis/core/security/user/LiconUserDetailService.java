package com.licon.redis.core.security.user;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.licon.redis.core.entity.Authority;
import com.licon.redis.core.entity.User;
import com.licon.redis.core.entity.UserAuthority;
import com.licon.redis.core.repository.persistence.AuthorityRepository;
import com.licon.redis.core.repository.persistence.UserAuthorityRepository;
import com.licon.redis.core.repository.persistence.UserRepository;

import org.springframework.data.util.Streamable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/29 14:18
 */
@Component
public class LiconUserDetailService implements UserDetailsService {
	private final UserRepository userRepository;
	private final UserAuthorityRepository userAuthorityRepository;
	private final AuthorityRepository authorityRepository;

	public LiconUserDetailService(UserRepository userRepository, UserAuthorityRepository userAuthorityRepository, AuthorityRepository authorityRepository) {
		this.userRepository = userRepository;
		this.userAuthorityRepository = userAuthorityRepository;
		this.authorityRepository = authorityRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findAllByUsername(username);

		Supplier<UsernameNotFoundException> notFound = () -> new UsernameNotFoundException(username+" not found!");

		User relUser = user.orElseThrow(notFound);
		Streamable<UserAuthority> userAuthority = userAuthorityRepository.findByUserId(relUser.getId());

		List<Authority> authorities = authorityRepository
				.findAllById(userAuthority.map(UserAuthority::getRoleId));

		relUser.setAuthorities(authorities);
		return new LiconUserDetail(relUser);
	}
}
