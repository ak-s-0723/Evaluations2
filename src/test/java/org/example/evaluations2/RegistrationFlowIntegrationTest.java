package org.example.evaluations2;

import org.example.evaluations2.models.User;
import org.example.evaluations2.publishers.RegistrationPublisher;
import org.example.evaluations2.services.IRegistrationService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RegistrationFlowIntegrationTest {

    @Autowired
    private RegistrationPublisher registrationPublisher;

    @SpyBean
    private IRegistrationService registrationService;

    @SpyBean
    private JavaMailSender mailSender;

    @Test
    void testPublishEvent_shouldTriggerListenerAndSendEmail() {
        // Arrange
        User user = new User();
        user.setEmail("anurag.khanna_1@scaler.com");
        user.setName("Anurag Khanna");
        user.setPassword("admin");

        // Act
        registrationPublisher.publishEvent(user);

        // Assert: token creation
        verify(registrationService, timeout(1000).times(1))
                .createVerificationToken(eq(user), any(String.class));

        // Assert: email sending
        ArgumentCaptor<SimpleMailMessage> emailCaptor =
                ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, timeout(1000).times(1)).send(emailCaptor.capture());

        SimpleMailMessage sentEmail = emailCaptor.getValue();
        assertThat(sentEmail.getTo()[0]).isEqualTo("anurag.khanna_1@scaler.com");
        assertThat(sentEmail.getSubject()).isEqualTo("Confirm Registration");
        assertThat(sentEmail.getText())
                .contains("Please click on below link to confirm registration.")
                .contains("http://localhost:8080/registrationConfirm?token=");
    }
}
