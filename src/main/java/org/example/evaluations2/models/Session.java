package org.example.evaluations2.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Session {
    private UUID id;
    private UUID userId;
    private State state;
    private String token;
}
