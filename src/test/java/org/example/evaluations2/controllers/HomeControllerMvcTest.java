package org.example.evaluations2.controllers;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(HomeController.class)
public class HomeControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHomeEndpointReturnsExpectedMessage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(content().string("You are logged in! " +
                        "If you want to Logout, Click <a href='/logout'>Here</a>"));
    }
}
