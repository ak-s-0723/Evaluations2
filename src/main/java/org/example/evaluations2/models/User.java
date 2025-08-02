package org.example.evaluations2.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> roles;
}
