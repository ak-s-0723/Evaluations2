package org.example.evaluations2.configs;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
public class AuthConfig {

    @Value("${secret.key}")
    private String secretKeyString;

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }
}
