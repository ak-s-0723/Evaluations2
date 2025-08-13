package org.example.evaluations2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.evaluations2.dtos.LoginRequestDto;
import org.example.evaluations2.dtos.LoginStatus;
import org.example.evaluations2.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void login_ShouldReturnSuccess_WhenUserServiceReturnsToken() throws Exception {
        // Arrange
        Mockito.when(userService.login(any(LoginRequestDto.class)))
                .thenReturn("FAKE_TOKEN_123");

        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        // Act & Assert
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(LoginStatus.SUCCESS.name())))
                .andExpect(jsonPath("$.token", is("FAKE_TOKEN_123")));
    }

    @Test
    void login_ShouldReturnFailure_WhenUserServiceThrowsException() throws Exception {
        // Arrange
        Mockito.when(userService.login(any(LoginRequestDto.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("test@example.com");
        request.setPassword("wrongPassword");

        // Act & Assert
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(LoginStatus.FAILURE.name())))
                .andExpect(jsonPath("$.token").doesNotExist());
    }
}
