package com.licon.redis.core.security.authority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import com.licon.redis.core.entity.Url;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

/**
 * Describe:提供用于投票授权的url角色
 *
 * @author Licon
 * @date 2022/7/4 10:02
 */
@Component
public class LiconSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		if (request.getRequestURI().equalsIgnoreCase("/test")){
			//查询url对应的所有操作权限
			return Collections.singletonList(new LiconConfigAttribute(
					new Url().setUrlPath("/test").setVoteType(VoteType.Affirmative), "ADMIN"));
		}

		return SecurityConfig.createList();
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
}
