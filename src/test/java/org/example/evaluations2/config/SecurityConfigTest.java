package org.example.evaluations2.config;

import org.example.evaluations2.handlers.UrlAuthenticationSuccessHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlAuthenticationSuccessHandler successHandler;

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public InMemoryUserDetailsManager userDetailsService() {
            UserDetails user = User.withUsername("user")
                    .password("{noop}pass") // {noop} means no password encoding
                    .roles("USER")
                    .build();
            return new InMemoryUserDetailsManager(user);
        }
    }

    @Test
    void whenNotAuthenticated_thenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/some-protected-endpoint"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }


    @Test
    void whenLoginSuccessful_thenSuccessHandlerInvoked() throws Exception {
        mockMvc.perform(formLogin().user("user").password("pass"))
                .andExpect(status().is2xxSuccessful()); // success → redirect

        verify(successHandler, times(1))
                .onAuthenticationSuccess(any(), any(), any());
    }
}
