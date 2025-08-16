package org.example.evaluations2.repos;

import org.example.evaluations2.models.UserVerificationToken;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TokenRepository {
    private final Map<String, UserVerificationToken> tokenMap;

    public TokenRepository() {
        tokenMap = new HashMap<>();
    }

    public UserVerificationToken save(UserVerificationToken userVerificationToken) {
        tokenMap.put(userVerificationToken.getToken(), userVerificationToken);
        return tokenMap.get(userVerificationToken.getToken());
    }
}
