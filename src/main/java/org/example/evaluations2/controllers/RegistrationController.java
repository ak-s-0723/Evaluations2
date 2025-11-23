package org.example.evaluations2.controllers;

import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.exceptions.UserAlreadyExistException;
import org.example.evaluations2.models.User;
import org.example.evaluations2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody UserDto user) {
        try {
            User response =  userService.registerNewUserAccount(user);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UserAlreadyExistException exception) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

}
