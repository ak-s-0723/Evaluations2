package org.example.evaluations2.services;

import org.example.evaluations2.models.Privilege;
import org.example.evaluations2.models.Role;
import org.example.evaluations2.models.User;
import org.example.evaluations2.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class StorageUserDetailsServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StorageUserDetailsService storageUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void loadUserByUsername_WhenUserExists_ReturnsUserDetails() {
        // Arrange
        Privilege readPrivilege = new Privilege();
        readPrivilege.setName("READ_PRIVILEGE");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        userRole.setPrivileges(List.of(readPrivilege));

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword123");
        user.setEnabled(true);
        user.setRoles(Collections.singletonList(userRole));

        when(userRepository.findByEmail("test@test.com")).thenReturn(user);

        // Act
        UserDetails userDetails = storageUserDetailsService.loadUserByUsername("test@test.com");

        // Assert
        assertThat(userDetails.getUsername()).isEqualTo("test@test.com");
        assertThat(userDetails.isEnabled()).isTrue();
        assertThat(userDetails.isAccountNonExpired()).isTrue();
        assertThat(userDetails.isAccountNonLocked()).isTrue();
        assertThat(userDetails.isCredentialsNonExpired()).isTrue();
        assertThat(userDetails.getAuthorities())
                .extracting("authority")
                .containsExactlyInAnyOrder("ROLE_USER", "READ_PRIVILEGE");
    }

    @Test
    void loadUserByUsername_WhenUserDoesNotExist_ThrowsException() {
        when(userRepository.findByEmail("notfound@test.com")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> storageUserDetailsService.loadUserByUsername("notfound@test.com"));
    }

    // -----------------------
    // Parameterized Tests
    // -----------------------

    static Stream<TestCase> rolePrivilegeProvider() {
        Privilege readPrivilege = new Privilege();
        readPrivilege.setName("READ_PRIVILEGE");

        Privilege writePrivilege = new Privilege();
        writePrivilege.setName("WRITE_PRIVILEGE");

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        adminRole.setPrivileges(Arrays.asList(readPrivilege, writePrivilege));

        Role staffRole = new Role();
        staffRole.setName("ROLE_STAFF");
        staffRole.setPrivileges(List.of(readPrivilege));

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        userRole.setPrivileges(Collections.emptyList());

        return Stream.of(
                new TestCase(List.of(adminRole), true,
                        List.of("ROLE_ADMIN", "READ_PRIVILEGE", "WRITE_PRIVILEGE")),
                new TestCase(List.of(staffRole), true,
                        List.of("ROLE_STAFF", "READ_PRIVILEGE")),
                new TestCase(List.of(userRole), true,
                        List.of("ROLE_USER")),
                new TestCase(Arrays.asList(staffRole, userRole), true,
                        List.of("ROLE_STAFF", "READ_PRIVILEGE", "ROLE_USER")),
                new TestCase(List.of(userRole), false,
                        List.of("ROLE_USER"))
        );
    }

    @ParameterizedTest
    @MethodSource("rolePrivilegeProvider")
    void loadUserByUsername_WithDifferentRolesAndEnabledFlag_ReturnsCorrectAuthorities(TestCase testCase) {
        // Arrange
        User user = new User();
        user.setEmail("param@test.com");
        user.setPassword("somePass");
        user.setEnabled(testCase.enabled);
        user.setRoles(testCase.roles);

        when(userRepository.findByEmail("param@test.com")).thenReturn(user);

        // Act
        UserDetails userDetails = storageUserDetailsService.loadUserByUsername("param@test.com");

        // Assert: authorities
        assertThat(userDetails.getAuthorities())
                .extracting("authority")
                .containsExactlyInAnyOrderElementsOf(testCase.expectedAuthorities);

        // Assert: flags
        assertThat(userDetails.isEnabled()).isEqualTo(testCase.enabled);
        assertThat(userDetails.isAccountNonExpired()).isTrue();
        assertThat(userDetails.isAccountNonLocked()).isTrue();
        assertThat(userDetails.isCredentialsNonExpired()).isTrue();
    }

    private static class TestCase {
        final List<Role> roles;
        final boolean enabled;
        final List<String> expectedAuthorities;

        TestCase(List<Role> roles, boolean enabled, List<String> expectedAuthorities) {
            this.roles = roles;
            this.enabled = enabled;
            this.expectedAuthorities = expectedAuthorities;
        }
    }
}
