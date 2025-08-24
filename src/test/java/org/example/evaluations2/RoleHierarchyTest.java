package org.example.evaluations2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RoleHierarchyTest {

    @Autowired
    private RoleHierarchy roleHierarchy;

    @Test
    void adminShouldInheritStaffRole() {
        Collection<? extends GrantedAuthority> authorities =
                roleHierarchy.getReachableGrantedAuthorities(
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                );

        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        assertThat(roles).contains("ROLE_ADMIN", "ROLE_STAFF", "ROLE_USER");
    }
}