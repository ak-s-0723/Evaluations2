package org.example.evaluations2.controllers;

import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.models.User;
import org.example.evaluations2.services.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    IRegistrationService registrationService;

    @PostMapping
    public ResponseEntity<User> registerNewUserAccount(@RequestBody UserDto accountDto) {
        return new ResponseEntity<>(registrationService.registerNewUserAccount(accountDto),
                HttpStatus.CREATED);
    }
}
