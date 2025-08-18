package org.example.evaluations2.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.evaluations2.models.User;
import org.example.evaluations2.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private DeviceService deviceService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("inside 1");
        loginNotification(authentication, request);
    }

    private void loginNotification(Authentication authentication,
                                   HttpServletRequest request) {
        try {
            System.out.println("inside 2");
           // if (authentication.getPrincipal() instanceof User) {
                deviceService.verifyDevice(((User)authentication.getPrincipal()), request);
           // }
        } catch(Exception e) {
            System.out.println("inside 3");
            throw new RuntimeException(e);
        }
    }
}
