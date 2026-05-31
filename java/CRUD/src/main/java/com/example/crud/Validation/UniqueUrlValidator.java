package com.example.crud.Validation;

import com.example.crud.Repository.LinkRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUrlValidator implements ConstraintValidator<UniqueUrl, String> {

    @Autowired
    private LinkRepository repository;

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url == null || url.isEmpty()) return true;
        return !repository.existsByTargetUrl(url);
    }
}