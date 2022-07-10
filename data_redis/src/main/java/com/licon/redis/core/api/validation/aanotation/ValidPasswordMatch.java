package com.licon.redis.core.api.validation.aanotation;


import com.licon.redis.core.api.validation.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Licon
 */
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface ValidPasswordMatch {
    String message() default "passwords are not the sam";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
