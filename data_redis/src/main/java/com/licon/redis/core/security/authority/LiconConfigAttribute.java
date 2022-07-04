package com.licon.redis.core.security.authority;


import com.licon.redis.core.entity.Url;

import org.springframework.security.access.ConfigAttribute;

/**
 * Describe: 获取url的实体对象
 *
 * @author Licon
 * @date 2022/7/4 15:23
 */
public class LiconConfigAttribute implements ConfigAttribute {

	private Url url;
	private String authority;

	public LiconConfigAttribute(Url url, String authority) {
		this.url = url;
		this.authority = authority;
	}

	@Override
	public String getAttribute() {
		return authority;
	}

	public Url getUrl() {
		return url;
	}
}
