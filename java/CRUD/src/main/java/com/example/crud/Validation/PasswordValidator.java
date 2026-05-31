package com.example.crud.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isEmpty()) {
            return true;
        }

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        if (password.length() < 10) {
            context.buildConstraintViolationWithTemplate("{error.password.length}").addConstraintViolation();
            isValid = false;
        }

        int lower = 0, upper = 0, digit = 0, special = 0;
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) lower++;
            else if (Character.isUpperCase(c)) upper++;
            else if (Character.isDigit(c)) digit++;
            else if (!Character.isWhitespace(c)) special++;
        }

        if (lower < 1) {
            context.buildConstraintViolationWithTemplate("{error.password.lower}").addConstraintViolation();
            isValid = false;
        }
        if (upper < 2) {
            context.buildConstraintViolationWithTemplate("{error.password.upper}").addConstraintViolation();
            isValid = false;
        }
        if (digit < 3) {
            context.buildConstraintViolationWithTemplate("{error.password.digit}").addConstraintViolation();
            isValid = false;
        }
        if (special < 4) {
            context.buildConstraintViolationWithTemplate("{error.password.special}").addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}