package com.licon.redis.core.api.exception;

import java.net.URI;

import com.licon.redis.core.util.Constants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Describe: 重复异常
 *
 * @author Licon
 * @date 2022/8/16 14:16
 */
public class DuplicateProblem extends AbstractThrowableProblem {


	private static final long serialVersionUID = 7470959868155834001L;

	private static final URI TYPE = URI.create(Constants.PROBLEM_PREFIX+"/duplicate");

	public DuplicateProblem(String title,String detail) {
		super(TYPE, title, Status.CONFLICT, detail);
	}
}
