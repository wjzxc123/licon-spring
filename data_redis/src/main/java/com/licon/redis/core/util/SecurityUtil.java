package com.licon.redis.core.util;

import java.security.Principal;
import java.util.Optional;

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
}
