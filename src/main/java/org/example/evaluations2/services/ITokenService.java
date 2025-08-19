package org.example.evaluations2.services;

import java.util.UUID;

public interface ITokenService {
    String generateJwt(UUID userId);
}
