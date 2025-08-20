package org.example.evaluations2.services;

import org.example.evaluations2.models.DeviceMetadata;
import org.example.evaluations2.repos.DeviceMetadataRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DeviceServiceTests {
    private DeviceService deviceService;
    private DeviceMetadataRepo deviceMetadataRepo;

    @BeforeEach
    void setUp() throws Exception {
        deviceService = new DeviceService();

        deviceMetadataRepo = mock(DeviceMetadataRepo.class);
        Field repoField = DeviceService.class.getDeclaredField("deviceMetadataRepository");
        repoField.setAccessible(true);
        repoField.set(deviceService, deviceMetadataRepo);
    }

    @Test
    void testFindDevicesByUserEmail() {
        // Arrange
        DeviceMetadata device1 = new DeviceMetadata();
        device1.setUserEmail("test@example.com");
        device1.setDeviceDetails("Chrome");

        DeviceMetadata device2 = new DeviceMetadata();
        device2.setUserEmail("test@example.com");
        device2.setDeviceDetails("Safari");

        List<DeviceMetadata> expectedDevices = Arrays.asList(device1, device2);

        when(deviceMetadataRepo.findByUserEmail("test@example.com"))
                .thenReturn(expectedDevices);

        // Act
        List<DeviceMetadata> result = deviceService.findDevicesByUserEmail("test@example.com");

        // Assert
        assertThat(result).hasSize(2).containsExactly(device1, device2);
        verify(deviceMetadataRepo, times(1)).findByUserEmail("test@example.com");
    }
}
