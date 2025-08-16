package org.example.evaluations2.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class User {
    private boolean enabled;

    private String email;

    private String name;

    private String password;

    private List<String> roles;

}
