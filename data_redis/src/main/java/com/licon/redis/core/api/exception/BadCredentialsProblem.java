package com.licon.redis.core.api.exception;

import java.net.URI;

import com.licon.redis.core.util.Constants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/16 16:05
 */
public class BadCredentialsProblem extends AbstractThrowableProblem {
	private static final long serialVersionUID = -140179248236267474L;

	private static final URI TYPE = URI.create(Constants.PROBLEM_PREFIX+"/bad-credentials");

	public BadCredentialsProblem() {
		super(TYPE, "认证失败", Status.UNAUTHORIZED, "用户名或密码错误!");
	}
}
