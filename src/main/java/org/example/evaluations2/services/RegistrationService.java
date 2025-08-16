package org.example.evaluations2.services;

import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.models.User;
import org.example.evaluations2.models.UserVerificationToken;
import org.example.evaluations2.repos.TokenRepository;
import org.example.evaluations2.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class RegistrationService implements IRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private final String confirmationMessage = "Your account has been registered successfully, Welcome to the platform";

    @Override
    public void createVerificationToken(User user, String token) {
       UserVerificationToken userVerificationToken = new UserVerificationToken();
       userVerificationToken.setUser(user);
       userVerificationToken.setToken(token);
       tokenRepository.save(userVerificationToken);
    }

    @Override
    public User registerNewUserAccount(UserDto userDto) {
        if(userRepository.findByEmail(userDto.getEmail()) == null) {
            User user = new User();
            user.setName(userDto.getName());
            user.setPassword(userDto.getPassword());
            user.setEmail(userDto.getEmail());
            user.setRoles(List.of("ROLE_USER"));
            return userRepository.save(user);
        }

        throw new RuntimeException("User Email already registered");
    }


    @Override
    public String confirmRegistration(String token) {
        System.out.println("Confirming user registration as user clicked on verification link in the email.");
        UserVerificationToken userVerificationToken = tokenRepository.findByValue(token);
        if (userVerificationToken == null) {
            throw new RuntimeException("Invalid Token passed");
        }

        User user = userVerificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((userVerificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new RuntimeException("Confirmation Link expired");
        }

        user.setEnabled(true);
        userRepository.save(user);
        return confirmationMessage;
    }
}
