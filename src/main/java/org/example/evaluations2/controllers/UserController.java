package org.example.evaluations2.controllers;

import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.models.User;
import org.example.evaluations2.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping
    public User registerNewUserAccount(@RequestBody  UserDto accountDto) {
        return userService.registerNewUserAccount(accountDto);
    }
}
