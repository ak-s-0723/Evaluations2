package org.example.evaluations2.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.evaluations2.dtos.LoginRequestDto;
import org.example.evaluations2.exceptions.PasswordMismatchException;
import org.example.evaluations2.exceptions.UserNotFoundException;
import org.example.evaluations2.models.User;
import org.example.evaluations2.repos.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    private LoginRequestDto loginRequestDto;
    private User savedUser;
    private MockedStatic<RandomStringUtils> mockedRandom;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock RandomStringUtils for deterministic results
        mockedRandom = mockStatic(RandomStringUtils.class);
        mockedRandom.when(() -> RandomStringUtils.randomAlphanumeric(15))
                .thenReturn("FAKE_RANDOM_123");

        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("test@example.com");
        loginRequestDto.setPassword("password123");

        savedUser = new User();
        savedUser.setEmail("test@example.com");
        savedUser.setPassword("$2a$10$encodedPasswordHere"); // dummy encoded password
    }

    @AfterEach
    void tearDown() {
        mockedRandom.close(); // Always close static mocks
    }

    @Test
    void login_ShouldReturnMockedRandomString_WhenCredentialsAreValid() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(savedUser);
        when(bCryptPasswordEncoder.matches("password123", savedUser.getPassword())).thenReturn(true);

        String result = userService.login(loginRequestDto);

        assertEquals("FAKE_RANDOM_123", result);
    }

    @Test
    void login_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.login(loginRequestDto));

        assertEquals("There is no account with an email address: test@example.com", exception.getMessage());
    }

    @Test
    void login_ShouldThrowPasswordMismatchException_WhenPasswordDoesNotMatch() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(savedUser);
        when(bCryptPasswordEncoder.matches("password123", savedUser.getPassword())).thenReturn(false);

        PasswordMismatchException exception = assertThrows(PasswordMismatchException.class,
                () -> userService.login(loginRequestDto));

        assertEquals("Please type correct password, or reset it", exception.getMessage());
    }
}
