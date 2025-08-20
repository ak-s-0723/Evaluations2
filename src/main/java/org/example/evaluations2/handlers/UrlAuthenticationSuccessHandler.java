package org.example.evaluations2.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.evaluations2.models.User;
import org.example.evaluations2.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private DeviceService deviceService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        loginNotification(authentication, request);
    }

    private void loginNotification(Authentication authentication,
                                   HttpServletRequest request)  {
        try {
            if (authentication.getPrincipal() instanceof UserDetails userDetails) {
                User user = new User();
                user.setEmail(userDetails.getUsername());
                user.setPassword(userDetails.getPassword());
                deviceService.verifyDevice(user, request);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
