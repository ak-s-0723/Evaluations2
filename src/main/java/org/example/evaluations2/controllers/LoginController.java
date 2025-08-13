package org.example.evaluations2.controllers;

import org.example.evaluations2.dtos.LoginRequestDto;
import org.example.evaluations2.dtos.LoginResponseDto;
import org.example.evaluations2.dtos.LoginStatus;
import org.example.evaluations2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        try {
            String token = userService.login(loginRequestDto);
            loginResponseDto.setStatus(LoginStatus.SUCCESS);
            loginResponseDto.setToken(token);
            return loginResponseDto;
        } catch (RuntimeException exception) {
            loginResponseDto.setStatus(LoginStatus.FAILURE);
            return loginResponseDto;
        }
    }
}
