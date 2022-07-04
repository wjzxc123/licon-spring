package com.licon.redis.core.security.authority;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * Describe: 管理员投票器，如果不含有管理员角色则直接拒绝
 *
 * @author Licon
 * @date 2022/7/4 10:49
 */
public class AdminVoter implements AccessDecisionVoter<Object>,VoterSequence {


	@Override
	public int getSequence() {
		return 1;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return (attribute.getAttribute() != null) && (attribute.getAttribute().equalsIgnoreCase("ADMIN"));
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		if (authentication == null) {
			return ACCESS_DENIED;
		}
		int result = ACCESS_DENIED;
		Collection<? extends GrantedAuthority> authorities = extractAuthorities(authentication);
		for (ConfigAttribute attribute : attributes) {
			if (this.supports(attribute)) {
				for (GrantedAuthority authority : authorities) {
					if (attribute.getAttribute().equalsIgnoreCase(authority.getAuthority())) {
						return ACCESS_GRANTED;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 扩展继承角色
	 * @param authentication
	 * @return
	 */
	Collection<? extends GrantedAuthority> extractAuthorities(Authentication authentication) {
		return authentication.getAuthorities();
	}
}
