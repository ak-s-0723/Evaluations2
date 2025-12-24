package org.example.evaluations2.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.verify;


public class CustomRedirectStrategyTests {

    @Test
    void testSendRedirect() throws IOException {
        // Arrange
        CustomRedirectStrategy redirectStrategy = new CustomRedirectStrategy();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        String targetUrl = "http://localhost:8080/devices/users/user";

        // Act
        redirectStrategy.sendRedirect(request, response, targetUrl);

        // Assert
        verify(response).sendRedirect(targetUrl);
    }
}