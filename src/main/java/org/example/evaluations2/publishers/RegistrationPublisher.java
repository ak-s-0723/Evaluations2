package org.example.evaluations2.publishers;

import org.example.evaluations2.events.OnRegistrationCompleteEvent;
import org.example.evaluations2.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class RegistrationPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private String appUrl = "http://localhost:8080";

    private Locale locale = Locale.ENGLISH;

    public void publishEvent(User registeredUser) {
        System.out.println("Publishing user registration event... ");
        OnRegistrationCompleteEvent onRegistrationCompleteEvent = new OnRegistrationCompleteEvent(registeredUser,
        locale, appUrl);
        applicationEventPublisher.publishEvent(onRegistrationCompleteEvent);
    }
}
