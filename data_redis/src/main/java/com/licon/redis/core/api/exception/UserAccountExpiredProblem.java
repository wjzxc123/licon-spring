package com.licon.redis.core.api.exception;

import java.net.URI;

import com.licon.redis.core.util.Constants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/16 15:04
 */
public class UserAccountExpiredProblem extends AbstractThrowableProblem {
	private static final long serialVersionUID = -5478626995552554772L;

	private static final URI TYPE = URI.create(Constants.PROBLEM_PREFIX+"/user-account-expired");

	public UserAccountExpiredProblem() {
		super(TYPE,"未授权访问", Status.UNAUTHORIZED,"账户已经过期！");
	}
}
