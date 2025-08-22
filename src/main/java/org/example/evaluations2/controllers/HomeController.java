package org.example.evaluations2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/home")
    @ResponseBody
    public String home() {
        return "You are logged in! " +
                "If you want to Logout, Click <a href='/logout'>Here</a>";
    }
}