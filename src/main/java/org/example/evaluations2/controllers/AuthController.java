package org.example.evaluations2.controllers;

import org.example.evaluations2.services.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private ITokenService tokenService;

    //Add your API here
}
