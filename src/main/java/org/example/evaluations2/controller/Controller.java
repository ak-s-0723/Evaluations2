package org.example.evaluations2.controller;

import org.example.evaluations2.handlers.UrlAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    UrlAuthenticationSuccessHandler urlAuthenticationSuccessHandler;

//    @GetMapping
//    public String sayHello() {
//        urlAuthenticationSuccessHandler.onAuthenticationSuccess();
//        return "Hello";
//    }
}
