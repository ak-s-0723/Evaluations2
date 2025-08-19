package org.example.evaluations2.configs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "secret.key=ScalerSecretKeyScalerSecretKey123"
})
public class SecurityConfigTest {
    @Autowired
    private SecretKey secretKey;

    @Test
    void testSecretKeyBeanCreated() {
        assertNotNull(secretKey, "SecretKey bean should be created");
        assertEquals("HmacSHA256", secretKey.getAlgorithm(), "Algorithm should be HS256 (HmacSHA256)");
    }
}
