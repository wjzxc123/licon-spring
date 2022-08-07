package com.licon.redis.core.api.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

/**
 * @author Licon
 */
@ControllerAdvice
public class SecurityExceptionHandler implements SecurityAdviceTrait {
}
