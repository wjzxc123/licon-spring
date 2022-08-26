package com.licon.redis.core.api.validation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.licon.redis.core.api.validation.aanotation.ValidEnumValue;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/26 16:52
 */
public class EnumValueValidator implements ConstraintValidator<ValidEnumValue,Enum<?>> {

	List<Enum<?>> valueList = null;

	@Override
	public void initialize(ValidEnumValue constraintAnnotation) {
		Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();

		List<String> enumValueList = Arrays.asList(constraintAnnotation.values());

		Enum<?>[] enumValues = enumClass.getEnumConstants();
		valueList = Arrays.stream(enumValues)
				.filter(e -> enumValueList.contains(e.name()))
				.collect(Collectors.toList());
	}

	@Override
	public boolean isValid( Enum<?> value, ConstraintValidatorContext context) {
		return valueList.contains(value);
	}
}
