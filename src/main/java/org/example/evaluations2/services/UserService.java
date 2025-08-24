package org.example.evaluations2.services;

import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.exceptions.EmailExistsException;
import org.example.evaluations2.models.User;
import org.example.evaluations2.repos.RoleRepository;
import org.example.evaluations2.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User registerNewUserAccount(UserDto accountDto) {
        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException
                    ("There is an account with that email address: " + accountDto.getEmail());
        }
        User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setEnabled(true); //important
        user.setTokenExpired(false); //important

        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return repository.save(user);
    }

    private Boolean emailExist(String email) {
        User user = repository.findByEmail(email);
        return user != null;
    }
}
