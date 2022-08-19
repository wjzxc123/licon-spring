package com.licon.redis.core.security.authentication;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.licon.redis.core.security.user.UserAuth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/19 15:48
 */
public class LiconAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 2095216175639484653L;

	private UserAuth userAuth;

	public LiconAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	public LiconAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

	public LiconAuthenticationToken(Object principal,Collection<? extends GrantedAuthority> authorities) {
		super(principal, null, authorities);
	}

	public LiconAuthenticationToken(UserAuth userAuth) {
		super(userAuth.getUserName(),null, Stream.of(userAuth.getUserRole()).map(String::valueOf).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		this.userAuth = userAuth;
	}

	public UserAuth getUserAuth() {
		return userAuth;
	}
}
