package com.licon.redis.core.api.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;


@RestControllerAdvice
public class ExceptionResolver implements ProblemHandling {
    @Override
    public ResponseEntity<Problem> handleThrowable(Throwable throwable, NativeWebRequest request) {
        if (throwable instanceof InsufficientAuthenticationException){
            return ResponseEntity.ok(new NoAuthenticationProblem());
        }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public boolean isCausalChainsEnabled() {
        return true;
    }
}