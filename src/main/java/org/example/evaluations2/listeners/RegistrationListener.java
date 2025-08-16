package org.example.evaluations2.listeners;

import lombok.NonNull;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.evaluations2.events.OnRegistrationCompleteEvent;
import org.example.evaluations2.models.User;
import org.example.evaluations2.services.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private IRegistrationService registrationService;

    @Autowired
    private JavaMailSender mailSender;

    private final Integer registrationTokenLength = 30;

    private final String emailMessage  = "Please click on below link to confirm registration.";

    @Override
    public void onApplicationEvent(@NonNull OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        //Add your Implementation here
    }
}
