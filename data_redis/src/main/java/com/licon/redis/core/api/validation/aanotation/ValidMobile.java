package com.licon.redis.core.api.validation.aanotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import com.licon.redis.core.api.validation.MobileValidator;
/**
 * @author Licon
 */
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
@Documented
public @interface ValidMobile {
    String message() default "{ValidMobile.userDto}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
