package org.example.evaluations2.services;

import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.models.User;

public interface IUserService {
    User registerNewUserAccount(UserDto accountDto);
}
