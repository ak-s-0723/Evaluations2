package org.example.evaluations2.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.evaluations2.services.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.RedirectStrategy;

import java.io.IOException;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

public class UrlAuthenticationSuccessHandlerTests {


    private DeviceService deviceService;
    private RedirectStrategy redirectStrategy;
    private UrlAuthenticationSuccessHandler successHandler;

    @BeforeEach
    void setUp() {
        deviceService = Mockito.mock(DeviceService.class);
        redirectStrategy = Mockito.mock(RedirectStrategy.class);

        successHandler = new UrlAuthenticationSuccessHandler();
        successHandler.deviceService = deviceService;
        successHandler.redirectStrategy = redirectStrategy;
    }

    @Test
    void testRedirectIsCalledWithCorrectUrl() throws IOException {
        // Arrange
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        User userDetails = new User("test@example.com", "secret", java.util.Collections.emptyList());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, "secret", userDetails.getAuthorities());

        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        verify(redirectStrategy).sendRedirect(
                eq(request),
                eq(response),
                eq("http://localhost:8080/devices/users/user")
        );
    }
}
