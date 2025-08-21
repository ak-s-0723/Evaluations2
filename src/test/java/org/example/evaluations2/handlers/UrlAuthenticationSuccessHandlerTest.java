package org.example.evaluations2.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UrlAuthenticationSuccessHandlerTest {

    private UrlAuthenticationSuccessHandler successHandler;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Authentication authentication;
    private HttpSession session;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        successHandler = new UrlAuthenticationSuccessHandler();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        authentication = mock(Authentication.class);
        session = mock(HttpSession.class);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    void testOnAuthenticationSuccess_WritesResponseAndClearsAttributes() throws Exception {
        when(authentication.getName()).thenReturn("testUser");
        when(request.getSession(false)).thenReturn(session);

        successHandler.onAuthenticationSuccess(request, response, authentication);

        assertEquals("testUser, Welcome !!", responseWriter.toString());

        verify(session, times(1)).removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        verify(response).setContentType("application/json");
    }

    @Test
    void testClearAuthenticationAttributes_WithSession() {
        when(request.getSession(false)).thenReturn(session);

        successHandler.clearAuthenticationAttributes(request);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(session, times(1)).removeAttribute(captor.capture());

        assertEquals(WebAttributes.AUTHENTICATION_EXCEPTION, captor.getValue());
    }
}
