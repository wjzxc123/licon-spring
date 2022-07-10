package com.licon.redis.core.api.validation;

import com.licon.redis.core.api.dto.UserDto;
import com.licon.redis.core.api.validation.aanotation.ValidPasswordMatch;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<ValidPasswordMatch, UserDto> {
    @Override
    public void initialize(ValidPasswordMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        return userDto.getPassword().equals(userDto.getMatchPassword());
    }
}
