package com.licon.redis.core.api.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.licon.redis.core.api.validation.aanotation.ValidMobile;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/16 13:07
 */
public class MobileValidator implements ConstraintValidator<ValidMobile,String> {

	private final static String MOBILE_PATTERN = "/^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$/";
	@Override
	public void initialize(ValidMobile constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return validMobile(value);
	}

	/**
	 * 根据正则校验手机号
	 * @param mobile
	 * @return
	 */
	private boolean validMobile(String mobile){
		Pattern pattern = Pattern.compile(MOBILE_PATTERN);
		Matcher matcher = pattern.matcher(mobile);
		return matcher.matches();
	}
}
