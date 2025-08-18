package org.example.evaluations2.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class User  {
    private UUID id;
    private String email;
    private String password;
}
