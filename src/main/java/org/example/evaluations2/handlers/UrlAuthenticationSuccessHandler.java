package org.example.evaluations2.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.evaluations2.models.User;
import org.example.evaluations2.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    DeviceService deviceService;

    @Autowired
    RedirectStrategy redirectStrategy;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        loginNotification(authentication, request);
        clearAuthenticationAttributes(request);
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

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
