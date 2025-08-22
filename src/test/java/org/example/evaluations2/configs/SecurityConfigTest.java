package org.example.evaluations2.configs;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.example.evaluations2.handlers.CustomLogoutSuccessHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomLogoutSuccessHandler logoutSuccessHandler;

    @Test
    void whenAccessRoot_thenNotFound() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenAccessLogin_thenOk() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    void whenAccessProtectedWithoutAuth_thenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void whenAccessProtectedWithAuth_thenOk() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    @Test
    void whenLogin_thenRedirectToHome() throws Exception {
        mockMvc.perform(formLogin().user("user").password("pass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithMockUser
    void whenLogout_thenCustomHandlerCalled() throws Exception {
        mockMvc.perform(logout("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
