package org.example.evaluations2.services;

import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.exceptions.UserAlreadyExistException;
import org.example.evaluations2.models.User;
import org.example.evaluations2.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john@example.com");
        userDto.setPassword("password123");
    }

    @Test
    void registerNewUserAccount_ShouldSaveUser_WhenEmailDoesNotExist() {
        // Arrange
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(null);
        when(bCryptPasswordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(UUID.randomUUID());
        savedUser.setFirstName(userDto.getFirstName());
        savedUser.setLastName(userDto.getLastName());
        savedUser.setEmail(userDto.getEmail());
        savedUser.setPassword("encodedPassword");
        savedUser.setRoles(List.of("ROLE_USER"));

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = userService.registerNewUserAccount(userDto);

        // Assert
        assertNotNull(result);
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        assertTrue(result.getRoles().contains("ROLE_USER"));

        verify(userRepository, times(1)).findByEmail(userDto.getEmail());
        verify(bCryptPasswordEncoder, times(1)).encode(userDto.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerNewUserAccount_ShouldThrowException_WhenEmailAlreadyExists() {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail(userDto.getEmail());
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(existingUser);

        // Act & Assert
        assertThrows(UserAlreadyExistException.class,
                () -> userService.registerNewUserAccount(userDto));

        verify(userRepository, times(1)).findByEmail(userDto.getEmail());
        verify(userRepository, never()).save(any(User.class));
        verify(bCryptPasswordEncoder, never()).encode(anyString());
    }
}
