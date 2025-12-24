package org.example.evaluations2.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.evaluations2.models.DeviceMetadata;
import org.example.evaluations2.services.DeviceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DeviceControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetDeviceDetails() throws Exception {
        // given
        DeviceMetadata device1 = new DeviceMetadata();
        device1.setId(UUID.randomUUID());
        device1.setUserEmail("test@example.com");
        device1.setDeviceDetails("Chrome on Windows");
        device1.setLocation("New Delhi");
        device1.setLastLoggedIn(new Date());

        DeviceMetadata device2 = new DeviceMetadata();
        device2.setId(UUID.randomUUID());
        device2.setUserEmail("test@example.com");
        device2.setDeviceDetails("Safari on Mac");
        device2.setLocation("Bangalore");
        device2.setLastLoggedIn(new Date());

        List<DeviceMetadata> devices = Arrays.asList(device1, device2);

        Mockito.when(deviceService.findDevicesByUserEmail(anyString()))
                .thenReturn(devices);

        // when + then
        mockMvc.perform(get("/devices/users/{userEmail}", "test@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(devices)));
    }
}