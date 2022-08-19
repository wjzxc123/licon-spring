package com.licon.redis.core.util;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import com.licon.redis.core.security.authentication.LiconAuthenticationToken;
import com.licon.redis.core.security.user.UserAuth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/7/19 10:38
 */
public class SecurityUtil {
	private final static String ANONYMOUS_USER = "anonymous";

	public static String getCurrentLogin(){
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.map(Authentication::getPrincipal)
				.map(principal->{
					if (principal instanceof UserDetails){
						UserDetails userDetails = (UserDetails) principal;
						return userDetails.getUsername();
					}
					if (principal instanceof Principal){
						return ((Principal) principal).getName();
					}

					//如果是其他情况则视为用户名
					return String.valueOf(principal);
				})
				.orElse(ANONYMOUS_USER);
	}

	public static UserAuth getCurrentUserInfo(){
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.map(authentication -> {
					if (authentication instanceof LiconAuthenticationToken){
						LiconAuthenticationToken liconAuthenticationToken = (LiconAuthenticationToken)authentication;
						return liconAuthenticationToken.getUserAuth();
					}
					//如果是其他情况则视为用户名
					return null;
				}).orElse(new UserAuth(0L,ANONYMOUS_USER, Collections.emptyList()));
	}
}
