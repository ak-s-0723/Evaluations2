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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

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
    void registerNewUserAccount_shouldThrowException_whenEmailExists() {
        // given
        when(userRepository.findByEmail("john@example.com"))
                .thenReturn(new User());

        // when & then
        UserAlreadyExistException ex = assertThrows(
                UserAlreadyExistException.class,
                () -> userService.registerNewUserAccount(userDto)
        );

        assertEquals("There is already an account with email address: john@example.com", ex.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerNewUserAccount_shouldSaveUser_whenEmailDoesNotExist() {
        // given
        when(userRepository.findByEmail("john@example.com"))
                .thenReturn(null);

        User savedUser = new User();
        savedUser.setFirstName("John");
        savedUser.setLastName("Doe");
        savedUser.setEmail("john@example.com");
        savedUser.setPassword("password123");
        savedUser.setRoles(List.of("ROLE_USER"));

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        // when
        User result = userService.registerNewUserAccount(userDto);

        // then
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("password123", result.getPassword());
        assertTrue(result.getRoles().contains("ROLE_USER"));

        verify(userRepository).save(any(User.class));
    }
}
