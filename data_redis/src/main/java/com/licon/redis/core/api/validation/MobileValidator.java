package com.licon.redis.core.api.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.licon.redis.core.api.validation.annotation.ValidMobile;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/16 13:07
 */
public class MobileValidator implements ConstraintValidator<ValidMobile,String> {

	private final static String MOBILE_PATTERN = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
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
