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
        User user = event.getUser();
        String token = RandomStringUtils.randomAlphanumeric(registrationTokenLength);
        registrationService.createVerificationToken(user, token);

        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm?token=" + token;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Confirm Registration");
        email.setText(emailMessage + "\r\n" + confirmationUrl);
        mailSender.send(email);
    }
}
