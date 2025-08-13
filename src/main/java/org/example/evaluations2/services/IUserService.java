package org.example.evaluations2.services;

import org.example.evaluations2.dtos.LoginRequestDto;

public interface IUserService {
    String login(LoginRequestDto loginRequestDto);
}
