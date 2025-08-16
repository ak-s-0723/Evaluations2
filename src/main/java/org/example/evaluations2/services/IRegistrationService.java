package org.example.evaluations2.services;

import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.models.User;
import org.example.evaluations2.models.UserVerificationToken;

public interface IRegistrationService {

    void createVerificationToken(User user, String token);

    User registerNewUserAccount(UserDto userDto);

    String confirmRegistration(String token);
}
