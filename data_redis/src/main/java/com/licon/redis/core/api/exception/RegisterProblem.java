package com.licon.redis.core.api.exception;

import java.net.URI;

import com.licon.redis.core.util.Constants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/11 16:48
 */
public class RegisterProblem extends AbstractThrowableProblem {
	private static final long serialVersionUID = -5736977861861333782L;

	private static final URI TYPE = URI.create(Constants.PROBLEM_PREFIX+"/register-fail");

	public RegisterProblem() {
		super(TYPE, "注册异常", Status.EXPECTATION_FAILED, "注册失败，请重新尝试");
	}
}
