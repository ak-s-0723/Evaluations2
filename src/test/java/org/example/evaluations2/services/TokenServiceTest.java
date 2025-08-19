package org.example.evaluations2.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.example.evaluations2.constants.JwtConstants;
import org.example.evaluations2.models.Session;
import org.example.evaluations2.models.State;
import org.example.evaluations2.repos.SessionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.crypto.SecretKey;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class TokenServiceTest {
    private TokenService tokenService;
    private SessionRepo sessionRepo;
    private SecretKey secretKey;

    @BeforeEach
    void setup() {
        sessionRepo = new SessionRepo();
        secretKey = Keys.hmacShaKeyFor("ScalerSecretKeyScalerSecretKey123".getBytes());
        tokenService = new TokenService();
        tokenService.secretKey = secretKey;
        tokenService.sessionRepo = sessionRepo;
    }

    private String createToken(long expiryMillis, String issuer) {
        return Jwts.builder()
                .claim(JwtConstants.EXPIRY_TIME, expiryMillis)
                .claim("sessionId", UUID.randomUUID().toString())
                .claim("userId", UUID.randomUUID().toString())
                .issuer(issuer)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    private Session saveSession(String token, State state) {
        Session session = new Session();
        session.setId(UUID.randomUUID());
        session.setUserId(UUID.randomUUID());
        session.setToken(token);
        session.setState(state);
        sessionRepo.save(session);
        return session;
    }

    @Test
    void validateToken_success() {
        long expiry = System.currentTimeMillis() + 10000;
        String token = createToken(expiry, JwtConstants.ORGANIZATION_NAME);
        saveSession(token, State.ACTIVE);

        assertDoesNotThrow(() -> tokenService.validateToken(token));
    }

    @Test
    void validateToken_invalidSession() {
        long expiry = System.currentTimeMillis() + 10000;
        String token = createToken(expiry, JwtConstants.ORGANIZATION_NAME);

        JwtException ex = assertThrows(JwtException.class,
                () -> tokenService.validateToken(token));
        assertEquals("Invalid Token", ex.getMessage());
    }

    @Test
    void validateToken_expiredSession() {
        long expiry = System.currentTimeMillis() - 10000;
        String token = createToken(expiry, JwtConstants.ORGANIZATION_NAME);
        saveSession(token, State.ACTIVE);

        JwtException ex = assertThrows(JwtException.class,
                () -> tokenService.validateToken(token));
        assertEquals("Token has expired", ex.getMessage());

        Session saved = sessionRepo.findSessionByToken(token);
        assertEquals(State.EXPIRED, saved.getState());
    }

    @Test
    void validateToken_invalidIssuer() {
        long expiry = System.currentTimeMillis() + 10000;
        String token = createToken(expiry, "FakeIssuer");
        saveSession(token, State.ACTIVE);

        JwtException ex = assertThrows(JwtException.class,
                () -> tokenService.validateToken(token));
        assertEquals("Invalid Issuer", ex.getMessage());
    }

    @Test
    void validateToken_sessionAlreadyExpiredState() {
        long expiry = System.currentTimeMillis() + 10000;
        String token = createToken(expiry, JwtConstants.ORGANIZATION_NAME);
        saveSession(token, State.EXPIRED);

        JwtException ex = assertThrows(JwtException.class,
                () -> tokenService.validateToken(token));
        assertEquals("Session Already expired", ex.getMessage());
    }
}
