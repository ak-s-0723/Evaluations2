package org.example.evaluations2.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.evaluations2.constants.JwtConstants;
import org.example.evaluations2.models.Session;
import org.example.evaluations2.models.State;
import org.example.evaluations2.repos.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenService implements ITokenService {

    @Autowired
    SecretKey secretKey;

    @Autowired
    SessionRepo sessionRepo;

    public String generateJwt(UUID userId) {
        UUID sessionId = UUID.randomUUID();
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtConstants.USER_ID,userId);
        claims.put(JwtConstants.SESSION,JwtConstants.SESSION_SOURCE+sessionId);
        claims.put(JwtConstants.ISSUER,JwtConstants.ORGANIZATION_NAME);
        long nowInMillis = System.currentTimeMillis();
        claims.put(JwtConstants.GENERATION_TIME,nowInMillis);
        claims.put(JwtConstants.EXPIRY_TIME,nowInMillis+JwtConstants.EXPIRY_WINDOW);

        String token = Jwts.builder().claims(claims).signWith(secretKey).compact();

        Session session = new Session();
        session.setId(sessionId);
        session.setToken(token);
        session.setUserId(userId);
        session.setState(State.ACTIVE);

        List<Session> userSessions = sessionRepo.findActiveSessionsByUserId(userId);
        for(Session userSession : userSessions) {
            userSession.setState(State.EXPIRED);
            sessionRepo.save(userSession);
        }

        sessionRepo.save(session);

        return token;
    }
}
