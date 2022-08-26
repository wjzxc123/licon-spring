package com.licon.redis.core.api.validation.aanotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.licon.redis.core.api.validation.EnumValueValidator;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/26 16:51
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy= EnumValueValidator.class)
public @interface ValidEnumValue {

	String message() default "{ValidEnumValue.enum}";
	Class<? extends Enum<?>> enumClazz();

	String[] values();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
