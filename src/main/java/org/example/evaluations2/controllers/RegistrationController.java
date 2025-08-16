package org.example.evaluations2.controllers;

import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.models.User;
import org.example.evaluations2.publishers.RegistrationPublisher;
import org.example.evaluations2.services.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class RegistrationController {

    @Autowired
    private IRegistrationService registrationService;

    @Autowired
    private RegistrationPublisher registrationPublisher;

    @PostMapping("/registration")
    public void registerUser(@RequestBody UserDto userDto) {
        User registeredUser = registrationService.registerNewUserAccount(userDto);
        registrationPublisher.publishEvent(registeredUser);
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token) {
        return registrationService.confirmRegistration(token);
    }
}
