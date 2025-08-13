package org.example.evaluations2.util;

import org.example.evaluations2.dtos.UserDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordMatchesValidatorTest {

    private PasswordMatchesValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordMatchesValidator();
    }

    @Test
    void whenPasswordsMatch_thenValidatorReturnsTrue() {
        UserDto dto = new UserDto();
        dto.setPassword("secret");
        dto.setMatchingPassword("secret");

        assertTrue(validator.isValid(dto, null));
    }

    @Test
    void whenPasswordsDoNotMatch_thenValidatorReturnsFalse() {
        UserDto dto = new UserDto();
        dto.setPassword("secret");
        dto.setMatchingPassword("different");

        assertFalse(validator.isValid(dto, null));
    }
}