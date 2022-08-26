package com.licon.redis.core.api.exception;

import com.licon.redis.core.util.Constants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

/**
 * @author Licon
 */
public class InvalidTotpProblem extends AbstractThrowableProblem {
    private static final long serialVersionUID = 3556143763101066174L;

    private static final URI TYPE = URI.create(Constants.PROBLEM_PREFIX+"/invalid-totp");

    public InvalidTotpProblem() {
        super(TYPE, "验证码错误", Status.UNAUTHORIZED, "验证码错误或已经过期");
    }
}
