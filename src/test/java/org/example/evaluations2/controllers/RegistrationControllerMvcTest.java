package org.example.evaluations2.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.models.User;
import org.example.evaluations2.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RegistrationController.class)
public class RegistrationControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenValidEmail_thenReturns200() throws Exception {
        UserDto dto = createValidUserDto();
        dto.setEmail("valid@example.com");

        Mockito.when(userService.registerNewUserAccount(any(UserDto.class)))
                .thenReturn(new User());

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void whenInvalidEmail_thenReturns400() throws Exception {
        UserDto dto = createValidUserDto();
        dto.setEmail("invalid-email");

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    private UserDto createValidUserDto() {
        UserDto dto = new UserDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPassword("secret");
        dto.setMatchingPassword("secret");
        dto.setEmail("test@example.com");
        return dto;
    }
}
