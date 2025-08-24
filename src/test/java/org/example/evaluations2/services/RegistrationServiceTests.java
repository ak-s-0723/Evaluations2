package org.example.evaluations2.services;


import org.example.evaluations2.dtos.UserDto;
import org.example.evaluations2.exceptions.EmailExistsException;
import org.example.evaluations2.models.Role;
import org.example.evaluations2.models.User;
import org.example.evaluations2.repos.RoleRepository;
import org.example.evaluations2.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegistrationServiceTests {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        registrationService = new RegistrationService();
        registrationService.userRepository = userRepository;
        registrationService.roleRepository = roleRepository;
    }

    @Test
    void whenEmailDoesNotExist_thenUserIsSaved() {
        // Given
        UserDto dto = new UserDto();
        dto.setEmail("new@example.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPassword("password");

        when(userRepository.findByEmail("new@example.com")).thenReturn(null); // email not found
        Role roleUser = new Role("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(roleUser);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("new@example.com");
        savedUser.setFirstName("John");
        savedUser.setLastName("Doe");
        savedUser.setPassword("password");
        savedUser.setEnabled(true);
        savedUser.setTokenExpired(false);
        savedUser.setRoles(Collections.singletonList(roleUser));

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        User result = registrationService.registerNewUserAccount(dto);

        // Then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("new@example.com");
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.isEnabled()).isTrue();
        assertThat(result.isTokenExpired()).isFalse();
        assertThat(result.getRoles()).extracting(Role::getName).containsExactly("ROLE_USER");

        verify(userRepository).findByEmail("new@example.com");
        verify(roleRepository).findByName("ROLE_USER");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void whenEmailExists_thenThrowEmailExistsException() {
        // Given
        UserDto dto = new UserDto();
        dto.setEmail("exists@example.com");
        dto.setFirstName("Jane");
        dto.setLastName("Smith");
        dto.setPassword("password");

        User existingUser = new User();
        existingUser.setEmail("exists@example.com");
        when(userRepository.findByEmail("exists@example.com")).thenReturn(existingUser);

        // When + Then
        assertThrows(EmailExistsException.class,
                () -> registrationService.registerNewUserAccount(dto));

        verify(userRepository, never()).save(any(User.class));
        verify(roleRepository, never()).findByName(anyString());
    }
}
