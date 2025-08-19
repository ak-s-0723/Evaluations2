package org.example.evaluations2.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.example.evaluations2.constants.JwtConstants;
import org.example.evaluations2.models.Session;
import org.example.evaluations2.models.State;
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
        Session session = sessionRepo.findSessionByToken(token);
        if (session==null) {
            throw new JwtException("Invalid Token");
        } else if(session.getState().equals(State.EXPIRED)) {
            throw new JwtException("Session Already expired");
        }

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();
        long expiry = (Long)claims.get(JwtConstants.EXPIRY_TIME);
        long currentTime = System.currentTimeMillis();

        if(currentTime > expiry) {
            session.setState(State.EXPIRED);
            sessionRepo.save(session);
            throw new JwtException("Token has expired");
        }

        String issuer = claims.get(JwtConstants.ISSUER, String.class);
        if (!JwtConstants.ORGANIZATION_NAME.equals(issuer)) {
            throw new JwtException("Invalid Issuer");
        }
    }
}
