package org.example.evaluations2.listeners;

import org.example.evaluations2.events.OnRegistrationCompleteEvent;
import org.example.evaluations2.models.User;
import org.example.evaluations2.services.IRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegistrationListenerTest {

    @Mock
    private IRegistrationService registrationService;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private RegistrationListener registrationListener;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("test@example.com");
        user.setName("John");
        user.setPassword("password123");
    }

    @Test
    void testOnApplicationEvent_shouldCreateTokenAndSendEmail() {
        // Arrange
        OnRegistrationCompleteEvent event =
                new OnRegistrationCompleteEvent(user, null, "http://localhost:8080");

        // Act
        registrationListener.onApplicationEvent(event);

        // Assert
        verify(registrationService, times(1))
                .createVerificationToken(eq(user), any(String.class));

        ArgumentCaptor<SimpleMailMessage> emailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(emailCaptor.capture());

        SimpleMailMessage sentEmail = emailCaptor.getValue();
        assert sentEmail.getTo()[0].equals("test@example.com");
        assert sentEmail.getSubject().equals("Confirm Registration");
        assert sentEmail.getText().contains("Please click on below link to confirm registration.");
        assert sentEmail.getText().contains("http://localhost:8080/registrationConfirm?token=");
    }
}
