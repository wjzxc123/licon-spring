package com.licon.redis.core.api.exception;

import java.net.URI;

import com.licon.redis.core.util.Constants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/10/14 13:51
 */
public class NoAuthenticationProblem extends AbstractThrowableProblem {

	private static final long serialVersionUID = 7470561090352203896L;

	private static final URI TYPE = URI.create(Constants.PROBLEM_PREFIX+"/no-authentication");


	public NoAuthenticationProblem() {
		super(TYPE, "未认证", Status.UNAUTHORIZED, "资源未认证");
	}
}
