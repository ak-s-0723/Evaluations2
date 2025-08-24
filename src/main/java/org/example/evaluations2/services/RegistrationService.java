package org.example.evaluations2.services;

import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.models.User;
import org.example.evaluations2.repos.RoleRepository;
import org.example.evaluations2.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService implements IRegistrationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public User registerNewUserAccount(UserDto accountDto) {
         //Add Implementation here
         return null;
    }
}
