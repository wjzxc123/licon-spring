package com.licon.redis.core.security.user;

import java.util.Optional;
import java.util.function.Supplier;

import com.licon.redis.core.entity.User;
import com.licon.redis.core.repository.persistence.AuthorityRepository;
import com.licon.redis.core.repository.persistence.UserRepository;

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
	private final AuthorityRepository authorityRepository;

	public LiconUserDetailService(UserRepository userRepository, AuthorityRepository authorityRepository) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findOptionalByUsername(username);

		Supplier<UsernameNotFoundException> notFound = () -> new UsernameNotFoundException(username+" not found!");

		/*User relUser = user.orElseThrow(notFound);
		Streamable<UserAuthority> userAuthority = userAuthorityRepository.findByUserId(relUser.getId());
		List<Authority> authorities = authorityRepository
				.findAllById(userAuthority.map(UserAuthority::getRoleId));
		relUser.setAuthorities(authorities);*/

		return user.orElseThrow(notFound);
	}
}
