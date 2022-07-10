package com.licon.redis.core.api.exception;



import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;


@RestControllerAdvice
public class ExceptionResolver implements ProblemHandling {
    @Override
    public boolean isCausalChainsEnabled() {
        return true;
    }
}