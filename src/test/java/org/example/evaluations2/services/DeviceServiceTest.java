package org.example.evaluations2.services;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import jakarta.servlet.http.HttpServletRequest;
import org.example.evaluations2.models.DeviceMetadata;
import org.example.evaluations2.models.User;
import org.example.evaluations2.repos.DeviceMetadataRepo;
import org.example.evaluations2.services.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import ua_parser.Client;
import ua_parser.OS;
import ua_parser.Parser;
import ua_parser.UserAgent;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class DeviceServiceTest {

    private DeviceService deviceService;
    private DatabaseReader dbReader;
    private DeviceMetadataRepo deviceMetadataRepo;
    private Parser parser;

    @BeforeEach
    void setUp() throws IOException {
        deviceService = new DeviceService();

        dbReader = mock(DatabaseReader.class);
        deviceMetadataRepo = mock(DeviceMetadataRepo.class);
        parser = mock(Parser.class);

        ReflectionTestUtils.setField(deviceService, "dbReader", dbReader);
        ReflectionTestUtils.setField(deviceService, "deviceMetadataRepository", deviceMetadataRepo);
        ReflectionTestUtils.setField(deviceService, "parser", parser);
    }

    @Test
    void testGetDeviceDetails_BuildsCorrectString() {
        UserAgent userAgent = new UserAgent("Chrome", "99", "0", null);
        OS os = new OS("Windows", "10", "0", null, null);
        Client client = new Client(userAgent, os, null);

        when(parser.parse("UA-STRING")).thenReturn(client);

        String details = ReflectionTestUtils.invokeMethod(deviceService, "getDeviceDetails", "UA-STRING");

        assertEquals("Chrome 99.0 - Windows 10.0", details);
    }

    @Test
    void testGetDeviceDetails_ReturnsEmptyOnNullClient() {
        when(parser.parse("UA-STRING")).thenReturn(null);

        String details = ReflectionTestUtils.invokeMethod(deviceService, "getDeviceDetails", "UA-STRING");

        assertEquals("", details);
    }

    @Test
    void testGetIpLocation_ReturnsCityName() throws Exception {
        InetAddress mockAddr = InetAddress.getByName("8.8.8.8");
        CityResponse cityResponse = mock(CityResponse.class);
        City city = mock(City.class);

        when(dbReader.city(mockAddr)).thenReturn(cityResponse);
        when(cityResponse.getCity()).thenReturn(city);
        when(city.getName()).thenReturn("New York");

        try (MockedStatic<InetAddress> inetMock = Mockito.mockStatic(InetAddress.class)) {
            inetMock.when(() -> InetAddress.getByName("8.8.8.8")).thenReturn(mockAddr);

            String location = ReflectionTestUtils.invokeMethod(deviceService, "getIpLocation", "8.8.8.8");
            assertEquals("New York", location);
        }
    }

    @Test
    void testFindExistingDevice_ReturnsMatchingDevice() {
        DeviceMetadata match = new DeviceMetadata();
        match.setDeviceDetails("Chrome 99.0 - Windows 10.0");
        match.setLocation("New York");

        DeviceMetadata other = new DeviceMetadata();
        other.setDeviceDetails("Firefox");
        other.setLocation("Paris");

        when(deviceMetadataRepo.findByUserEmail("user@example.com"))
                .thenReturn(List.of(other, match));

        DeviceMetadata found = ReflectionTestUtils.invokeMethod(
                deviceService, "findExistingDevice",
                "user@example.com", "Chrome 99.0 - Windows 10.0", "New York");

        assertEquals(match, found);
    }

    @Test
    void testFindExistingDevice_ReturnsNullIfNotFound() {
        when(deviceMetadataRepo.findByUserEmail("user@example.com"))
                .thenReturn(Collections.emptyList());

        DeviceMetadata found = ReflectionTestUtils.invokeMethod(
                deviceService, "findExistingDevice",
                "user@example.com", "UA", "Paris");

        assertNull(found);
    }

    @Test
    void testVerifyDevice_CreatesNewDevice() throws Exception {
        User user = new User();
        user.setEmail("user@example.com");

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("8.8.8.8");
        when(request.getHeader("user-agent")).thenReturn("UA-STRING");

        when(parser.parse("UA-STRING")).thenReturn(new Client(
                new UserAgent("Chrome", "99", "0", null),
                new OS("Windows", "10", "0", null, null),
                null
        ));

        InetAddress mockAddr = InetAddress.getByName("8.8.8.8");
        CityResponse cityResponse = mock(CityResponse.class);
        City city = mock(City.class);

        when(dbReader.city(mockAddr)).thenReturn(cityResponse);
        when(cityResponse.getCity()).thenReturn(city);
        when(city.getName()).thenReturn("New York");

        when(deviceMetadataRepo.findByUserEmail("user@example.com"))
                .thenReturn(Collections.emptyList());

        deviceService.verifyDevice(user, request);

        ArgumentCaptor<DeviceMetadata> captor = ArgumentCaptor.forClass(DeviceMetadata.class);
        verify(deviceMetadataRepo).save(captor.capture());

        DeviceMetadata saved = captor.getValue();
        assertEquals("user@example.com", saved.getUserEmail());
        assertEquals("New York", saved.getLocation());
        assertTrue(saved.getDeviceDetails().contains("Chrome"));
        assertNotNull(saved.getLastLoggedIn());
    }

    @Test
    void testVerifyDevice_UpdatesExistingDevice() throws Exception {
        User user = new User();
        user.setEmail("user@example.com");

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("8.8.8.8");
        when(request.getHeader("user-agent")).thenReturn("UA-STRING");

        when(parser.parse("UA-STRING")).thenReturn(new Client(
                new UserAgent("Chrome", "99", "0", null),
                new OS("Windows", "10", "0", null, null),
                null
        ));

        InetAddress mockAddr = InetAddress.getByName("8.8.8.8");
        CityResponse cityResponse = mock(CityResponse.class);
        City city = mock(City.class);

        when(dbReader.city(mockAddr)).thenReturn(cityResponse);
        when(cityResponse.getCity()).thenReturn(city);
        when(city.getName()).thenReturn("New York");

        DeviceMetadata existing = new DeviceMetadata();
        existing.setDeviceDetails("Chrome 99.0 - Windows 10.0");
        existing.setLocation("New York");
        existing.setUserEmail("user@example.com");

        when(deviceMetadataRepo.findByUserEmail("user@example.com"))
                .thenReturn(List.of(existing));

        deviceService.verifyDevice(user, request);

        verify(deviceMetadataRepo).save(existing);
        assertNotNull(existing.getLastLoggedIn());
    }
}
