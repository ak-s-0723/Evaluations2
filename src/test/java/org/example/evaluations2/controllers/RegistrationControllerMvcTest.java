package org.example.evaluations2.controllers;

import org.example.evaluations2.publishers.RegistrationPublisher;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.example.evaluations2.services.IRegistrationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = RegistrationController.class)
public class RegistrationControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRegistrationService registrationService;

    @MockBean
    private RegistrationPublisher registrationPublisher;


    @Test
    void testConfirmRegistration_withValidToken_shouldReturnSuccessMessage() throws Exception {
        // Arrange
        String token = "validToken123";
        Mockito.when(registrationService.confirmRegistration(eq(token)))
                .thenReturn("Registration confirmed successfully");

        // Act & Assert
        mockMvc.perform(get("/registrationConfirm")
                        .param("token", token))
                .andExpect(status().isOk())
                .andExpect(content().string("Registration confirmed successfully"));

        Mockito.verify(registrationService).confirmRegistration(token);
    }
}
