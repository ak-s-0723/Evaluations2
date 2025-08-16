package org.example.evaluations2.services;

import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.models.User;

public interface IRegistrationService {

    void createVerificationToken(User user, String token);

    User registerNewUserAccount(UserDto userDto);
}
