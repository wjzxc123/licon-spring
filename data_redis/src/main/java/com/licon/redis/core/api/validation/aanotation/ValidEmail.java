package com.licon.redis.core.api.validation.aanotation;

import com.licon.redis.core.api.validation.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Licon
 */
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    String message() default "{ValidEmail.userDto}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
