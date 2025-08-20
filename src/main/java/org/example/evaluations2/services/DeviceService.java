package org.example.evaluations2.services;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.example.evaluations2.models.DeviceMetadata;
import org.example.evaluations2.models.User;
import org.example.evaluations2.repos.DeviceMetadataRepo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ua_parser.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import ua_parser.Parser;

import static java.util.Objects.nonNull;

@Service
public class DeviceService {

    private final DatabaseReader dbReader;

    private final DeviceMetadataRepo deviceMetadataRepository;

    private final Parser parser;

    public DeviceService() throws IOException {
        ClassPathResource resource = new ClassPathResource("geoip/GeoLite2-City.mmdb");
        dbReader = new DatabaseReader.Builder(resource.getFile()).build();
        deviceMetadataRepository = new DeviceMetadataRepo();
        parser = new Parser();
    }

    private String getDeviceDetails(String userAgent) {
        String deviceDetails = "";

        Client client = parser.parse(userAgent);
        if (nonNull(client)) {
            deviceDetails = client.userAgent.family
                    + " " + client.userAgent.major + "."
                    + client.userAgent.minor + " - "
                    + client.os.family + " " + client.os.major
                    + "." + client.os.minor;
        }
        return deviceDetails;
    }

    public void verifyDevice(User user, HttpServletRequest request) throws IOException, GeoIp2Exception {
        String ip = extractIp(request);

        String location;
        if(!ip.equals("0:0:0:0:0:0:0:1"))
            location = getIpLocation(ip);
        else
            location = "0:0:0:0:0:0:0:1";  //localhost

        String deviceDetails = getDeviceDetails(request.getHeader("user-agent"));

        DeviceMetadata existingDevice
                = findExistingDevice(user.getEmail(), deviceDetails, location);

        if (Objects.isNull(existingDevice)) {
            DeviceMetadata deviceMetadata = new DeviceMetadata();
            deviceMetadata.setId(UUID.randomUUID());
            deviceMetadata.setUserEmail(user.getEmail());
            deviceMetadata.setLocation(location);
            deviceMetadata.setDeviceDetails(deviceDetails);
            deviceMetadata.setLastLoggedIn(new Date());
            deviceMetadataRepository.save(deviceMetadata);
        } else {
            existingDevice.setLastLoggedIn(new Date());
            deviceMetadataRepository.save(existingDevice);
        }
    }

    private DeviceMetadata findExistingDevice(
            String userEmail, String deviceDetails, String location) {
        List<DeviceMetadata> knownDevices
                = deviceMetadataRepository.findByUserEmail(userEmail);

        for (DeviceMetadata existingDevice : knownDevices) {
            if (existingDevice.getDeviceDetails().equals(deviceDetails)
                    && existingDevice.getLocation().equals(location)) {
                return existingDevice;
            }
        }
        return null;
    }

    private String extractIp(HttpServletRequest request) {
        String clientIp;
        String clientXForwardedForIp = request
                .getHeader("x-forwarded-for");
        if (nonNull(clientXForwardedForIp)) {
            clientIp = clientXForwardedForIp.split(",")[0].trim();
        } else {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    private String getIpLocation(String ip) throws IOException, GeoIp2Exception {
        String location = "";
        InetAddress ipAddress = InetAddress.getByName(ip);

        CityResponse cityResponse = dbReader.city(ipAddress);

        if (nonNull(cityResponse) &&
                nonNull(cityResponse.getCity())) {
            location = cityResponse.getCity().getName();
        }
        return location;
    }
}
