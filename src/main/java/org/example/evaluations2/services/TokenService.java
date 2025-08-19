package org.example.evaluations2.services;

import org.example.evaluations2.repos.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;

@Service
public class TokenService implements ITokenService {

    @Autowired
    SecretKey secretKey;

    @Autowired
    SessionRepo sessionRepo;

    public void validateToken(String token) {
        //Add your implementation here
    }
}
