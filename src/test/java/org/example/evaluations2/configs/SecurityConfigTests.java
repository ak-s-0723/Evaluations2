package org.example.evaluations2.configs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTests {

    @Autowired
    private RoleHierarchy roleHierarchy;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void roleHierarchy_ShouldDefineAdminAboveStaffAndStaffAboveUser() {
        var auth = new TestingAuthenticationToken("admin", "pass", "ROLE_ADMIN");

        var reachable = roleHierarchy.getReachableGrantedAuthorities(auth.getAuthorities());

        List<String> roles = reachable.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        assertThat(roles).contains("ROLE_ADMIN", "ROLE_STAFF", "ROLE_USER");
    }

    @Test
    void passwordEncoder_ShouldEncodeAndMatchPasswords() {
        String rawPassword = "secret123";

        String encoded = passwordEncoder.encode(rawPassword);

        assertThat(encoded).isNotEqualTo(rawPassword);
        assertThat(passwordEncoder.matches(rawPassword, encoded)).isTrue();
    }

    @Test
    void securityFilterChain_ShouldBeCreated() {
        assertThat(securityFilterChain).isNotNull();
    }
    

    @Test
    void registerEndpoint_ShouldBeAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\",\"password\":\"123\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void roleHierarchyEndpoint_ShouldRequireStaffRole() throws Exception {
        mockMvc.perform(get("/roleHierarchy"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/roleHierarchy").with(user("user").roles("USER")))
                .andExpect(status().isForbidden());

    }

    @Test
    void otherEndpoints_ShouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/secure"))
                .andExpect(status().isUnauthorized());
    }
}
