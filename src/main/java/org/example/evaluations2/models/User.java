package org.example.evaluations2.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> roles;
}
