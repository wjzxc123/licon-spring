package com.licon.redis.core.api.exception;

import java.net.URI;

import com.licon.redis.core.util.Constants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/16 15:05
 */
public class UserCredentialsExpiredProblem extends AbstractThrowableProblem {
	private static final long serialVersionUID = -3253888491510715906L;

	private static final URI TYPE = URI.create(Constants.PROBLEM_PREFIX+"/user-expired");

	public UserCredentialsExpiredProblem() {
		super(TYPE,"未授权访问", Status.UNAUTHORIZED,"凭证已过期！");
	}
}
