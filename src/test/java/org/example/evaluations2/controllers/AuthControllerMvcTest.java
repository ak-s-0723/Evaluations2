package org.example.evaluations2.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.evaluations2.dtos.RequestDto;
import org.example.evaluations2.dtos.ResponseStatus;
import org.example.evaluations2.services.ITokenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ITokenService tokenService;

    @Test
    void shouldReturnSuccessResponseWithAuthorizationHeader() throws Exception {
        // Arrange
        UUID userId = UUID.randomUUID();
        RequestDto requestDto = new RequestDto();
        requestDto.setUserId(userId);

        String mockToken = "mock-jwt-token";

        Mockito.when(tokenService.generateJwt(userId))
                .thenReturn(mockToken);

        // Act & Assert
        mockMvc.perform(post("/authToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + mockToken))
                .andExpect(jsonPath("$.status", is(ResponseStatus.SUCCESS.name())));
    }

    @Test
    void shouldReturnFailureResponseWhenExceptionThrown() throws Exception {
        // Arrange
        UUID userId = UUID.randomUUID();
        RequestDto requestDto = new RequestDto();
        requestDto.setUserId(userId);

        Mockito.when(tokenService.generateJwt(userId))
                .thenThrow(new RuntimeException("Token generation failed"));

        // Act & Assert
        mockMvc.perform(post("/authToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is(ResponseStatus.FAILURE.name())));
    }
}
