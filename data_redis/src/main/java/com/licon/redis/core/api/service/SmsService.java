package com.licon.redis.core.api.service;

import javax.validation.constraints.NotBlank;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/17 16:42
 */
public interface SmsService {
	void send(@NotBlank String mobile,String msg);
}
