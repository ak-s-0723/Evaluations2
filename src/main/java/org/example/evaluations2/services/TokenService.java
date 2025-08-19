package org.example.evaluations2.services;

import org.example.evaluations2.repos.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.UUID;

@Service
public class TokenService implements ITokenService {

    @Autowired
    SessionRepo sessionRepo;

    @Autowired
    SecretKey secretKey;  // Will error as bean not present

    public String generateJwt(UUID userId) {
        //Add your implementation here
        return null;
    }
}
