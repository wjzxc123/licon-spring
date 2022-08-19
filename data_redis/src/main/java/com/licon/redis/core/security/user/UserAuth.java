package com.licon.redis.core.security.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/19 15:50
 */
@Getter
@AllArgsConstructor
public class UserAuth {
	private Long userId;

	private String userName;

	private List<String> userRole;
}
