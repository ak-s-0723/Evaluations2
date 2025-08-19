package org.example.evaluations2.controllers;

import io.jsonwebtoken.JwtException;
import org.example.evaluations2.dtos.ResponseStatus;
import org.example.evaluations2.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenService tokenService;

    private String validToken;

    private String expiredToken;

    @BeforeEach
    void setup() {
        validToken = "validToken";
        expiredToken = "expiredToken";
    }

    @Test
    void validateToken_success() throws Exception {
        doNothing().when(tokenService).validateToken(validToken);

        mockMvc.perform(get("/validateToken")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name()))
                .andExpect(jsonPath("$.message").value(""));
    }

    @Test
    void validateToken_invalidToken() throws Exception {
        doThrow(new JwtException("Invalid Token"))
                .when(tokenService).validateToken(expiredToken);

        mockMvc.perform(get("/validateToken")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + expiredToken))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(ResponseStatus.FAILURE.name()))
                .andExpect(jsonPath("$.message").value("Invalid Token"));
    }

    @Test
    void validateToken_missingHeader() throws Exception {
        mockMvc.perform(get("/validateToken"))
                .andExpect(status().isBadRequest());
    }
}
