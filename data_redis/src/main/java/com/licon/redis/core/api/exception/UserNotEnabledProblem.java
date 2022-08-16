package com.licon.redis.core.api.exception;

import java.net.URI;

import com.licon.redis.core.util.Constants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/16 14:53
 */
public class UserNotEnabledProblem extends AbstractThrowableProblem {
	private static final long serialVersionUID = -4023171000245247068L;

	private static final URI TYPE = URI.create(Constants.PROBLEM_PREFIX+"/user-not-enable");

	public UserNotEnabledProblem() {
		super(TYPE,"未授权访问", Status.UNAUTHORIZED,"用户未激活！");
	}
}
