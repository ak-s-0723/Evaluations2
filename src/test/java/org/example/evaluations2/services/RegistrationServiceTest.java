package org.example.evaluations2.services;

import org.example.evaluations2.models.User;
import org.example.evaluations2.models.UserVerificationToken;
import org.example.evaluations2.repos.TokenRepository;
import org.example.evaluations2.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private RegistrationService registrationService;

    private User user;
    private UserVerificationToken token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setEmail("test@example.com");
        user.setName("John Doe");
        user.setPassword("password123");
        user.setEnabled(false);

        token = new UserVerificationToken();
        token.setUser(user);
        token.setToken("validToken");
    }

    @Test
    void testConfirmRegistration_validToken_shouldEnableUserAndReturnMessage() {
        // Arrange
        when(tokenRepository.findByValue("validToken")).thenReturn(token);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        String result = registrationService.confirmRegistration("validToken");

        // Assert
        assertThat(result).isEqualTo("Your account has been registered successfully, Welcome to the platform");
        assertThat(user.isEnabled()).isTrue();
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testConfirmRegistration_invalidToken_shouldThrowException() {
        // Arrange
        when(tokenRepository.findByValue("invalidToken")).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> registrationService.confirmRegistration("invalidToken"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Invalid Token passed");

        verify(userRepository, never()).save(any());
    }

    @Test
    void testConfirmRegistration_expiredToken_shouldThrowException() {
        // Arrange
        token.setExpiryDate(new Date(System.currentTimeMillis() - 1000)); // expired
        when(tokenRepository.findByValue("expiredToken")).thenReturn(token);

        // Act & Assert
        assertThatThrownBy(() -> registrationService.confirmRegistration("expiredToken"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Confirmation Link expired");

        verify(userRepository, never()).save(any());
    }
}
