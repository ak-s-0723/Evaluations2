package org.example.evaluations2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.exceptions.UserAlreadyExistException;
import org.example.evaluations2.models.User;
import org.example.evaluations2.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RegistrationControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerUser_success() throws Exception {
        // Given
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setPassword("password123");

        User savedUser = new User();
        savedUser.setId(UUID.randomUUID());
        savedUser.setFirstName("John");
        savedUser.setLastName("Doe");
        savedUser.setEmail("test@example.com");
        savedUser.setRoles(List.of("ROLE_USER"));

        when(userService.registerNewUserAccount(any(UserDto.class)))
                .thenReturn(savedUser);

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }


    @Test
    void registerUser_alreadyExists() throws Exception {
        UserDto dto = new UserDto();
        dto.setEmail("existing@gmail.com");
        dto.setPassword("password");

        when(userService.registerNewUserAccount(any(UserDto.class)))
                .thenThrow(new UserAlreadyExistException("User already exists"));

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(content().string(""));
    }
}
