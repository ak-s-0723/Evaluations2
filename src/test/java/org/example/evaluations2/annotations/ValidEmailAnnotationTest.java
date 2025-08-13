package org.example.evaluations2.annotations;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.evaluations2.dtos.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ValidEmailAnnotationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenValidEmail_thenNoViolations() {
        UserDto dto = createValidUserDto();
        dto.setEmail("valid.email@example.com");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenInvalidEmail_thenViolationMessageMatches() {
        UserDto dto = createValidUserDto();
        dto.setEmail("invalid-email");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(
                violations.stream().anyMatch(v -> v.getMessage().equals("Invalid email"))
        );
    }

    private UserDto createValidUserDto() {
        UserDto dto = new UserDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPassword("secret");
        dto.setMatchingPassword("secret");
        dto.setEmail("valid@example.com");
        return dto;
    }
}
