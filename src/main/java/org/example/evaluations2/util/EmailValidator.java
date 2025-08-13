package org.example.evaluations2.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.evaluations2.annotations.ValidEmail;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        return true;
    }
    private boolean validateEmail(String email) {
        return true;
    }
}
