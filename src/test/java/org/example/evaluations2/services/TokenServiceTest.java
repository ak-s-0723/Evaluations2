package org.example.evaluations2.services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.evaluations2.constants.JwtConstants;
import org.example.evaluations2.models.Session;
import org.example.evaluations2.models.State;
import org.example.evaluations2.repos.SessionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TokenServiceTest {

    private TokenService tokenService;
    private SessionRepo sessionRepo;
    private SecretKey secretKey;

    @BeforeEach
    void setUp() {
        sessionRepo = Mockito.mock(SessionRepo.class);
        secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256); // generate real key

        tokenService = new TokenService();
        tokenService.secretKey = secretKey;
        tokenService.sessionRepo = sessionRepo;
    }

    @Test
    void shouldGenerateJwtAndSaveSession() {
        // Arrange
        UUID userId = UUID.randomUUID();
        when(sessionRepo.findActiveSessionsByUserId(userId))
                .thenReturn(Collections.emptyList());

        // Act
        String token = tokenService.generateJwt(userId);

        // Assert
        // Verify JWT is valid
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertThat(claims.get(JwtConstants.USER_ID, String.class)).isEqualTo(userId.toString());
        assertThat(claims.get(JwtConstants.ISSUER, String.class)).isEqualTo(JwtConstants.ORGANIZATION_NAME);
        assertThat(claims.get(JwtConstants.SESSION, String.class)).startsWith(JwtConstants.SESSION_SOURCE);
        assertThat(claims.get(JwtConstants.GENERATION_TIME, Long.class)).isNotNull();
        assertThat(claims.get(JwtConstants.EXPIRY_TIME, Long.class)).isGreaterThan(claims.get(JwtConstants.GENERATION_TIME, Long.class));

        // Verify sessionRepo.save called with ACTIVE session
        ArgumentCaptor<Session> sessionCaptor = ArgumentCaptor.forClass(Session.class);
        verify(sessionRepo).save(sessionCaptor.capture());
        Session savedSession = sessionCaptor.getValue();

        assertThat(savedSession.getUserId()).isEqualTo(userId);
        assertThat(savedSession.getToken()).isEqualTo(token);
        assertThat(savedSession.getState()).isEqualTo(State.ACTIVE);
    }

    @Test
    void shouldExpireExistingSessionsBeforeSavingNewOne() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Session oldSession = new Session();
        oldSession.setId(UUID.randomUUID());
        oldSession.setUserId(userId);
        oldSession.setToken("old-token");
        oldSession.setState(State.ACTIVE);

        when(sessionRepo.findActiveSessionsByUserId(userId))
                .thenReturn(List.of(oldSession));

        // Act
        tokenService.generateJwt(userId);

        // Assert
        // verify old session expired
        verify(sessionRepo, atLeastOnce()).save(oldSession);
        assertThat(oldSession.getState()).isEqualTo(State.EXPIRED);

        // verify new session also saved
        verify(sessionRepo, atLeast(2)).save(any(Session.class));
    }
}
