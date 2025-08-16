package org.example.evaluations2.publishers;

import org.example.evaluations2.events.OnRegistrationCompleteEvent;
import org.example.evaluations2.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class RegistrationPublisherTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private RegistrationPublisher registrationPublisher;

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
    void testPublishEvent_shouldPublishRegistrationEvent() {
        // Act
        registrationPublisher.publishEvent(user);

        // Assert
        verify(applicationEventPublisher, times(1))
                .publishEvent(any(OnRegistrationCompleteEvent.class));
    }
}
