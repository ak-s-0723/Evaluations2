package org.example.evaluations2.util;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {
    private EmailValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EmailValidator();
        validator.initialize(null);
    }

    @Test
    void whenValidEmail_thenReturnsTrue() {
        assertTrue(validator.isValid("test@example.com", null));
        assertTrue(validator.isValid("john.doe@mail.co", null));
        assertTrue(validator.isValid("user+label@sub.domain.org", null));
    }

    @Test
    void whenInvalidEmail_thenReturnsFalse() {
        assertFalse(validator.isValid("plainaddress", null));
        assertFalse(validator.isValid("john@.com", null));
        assertFalse(validator.isValid("@missingusername.com", null));
        assertFalse(validator.isValid("missingatsign.com", null));
    }

    @Test
    void whenEmpty_thenReturnsFalse() {
        assertFalse(validator.isValid("", null));
        assertFalse(validator.isValid("   ", null));
    }
}
