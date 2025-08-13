package org.example.evaluations2.services;


import org.example.evaluations2.models.User;
import org.springframework.stereotype.Service;
import org.example.evaluations2.dtos.UserDto;


@Service
public class UserService implements IUserService {

    @Override
    public User registerNewUserAccount(UserDto userDto)  {
        return null;
    }
}
