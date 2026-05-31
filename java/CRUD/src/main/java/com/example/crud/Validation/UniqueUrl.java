package com.example.crud.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueUrlValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUrl {
    String message() default "{error.url.unique}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}