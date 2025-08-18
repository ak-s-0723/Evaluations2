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

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        //File database = new File("/Users/anuragkhanna/Downloads/GeoLite2-City_20250815/GeoLite2-City.mmdb");
        //dbReader = new DatabaseReader.Builder(database).build();
        deviceMetadataRepository = new DeviceMetadataRepo();
        parser = new Parser();
    }

    private String getDeviceDetails(String userAgent) {
        System.out.println("inside 4");
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
        System.out.println("inside 5");
        String ip = extractIp(request);
        String location = getIpLocation(ip);

        String deviceDetails = getDeviceDetails(request.getHeader("user-agent"));

        DeviceMetadata existingDevice
                = findExistingDevice(user.getId(), deviceDetails, location);

        if (Objects.isNull(existingDevice)) {
//            unknownDeviceNotification(deviceDetails, location,
//                    ip, user.getEmail(), request.getLocale());

            System.out.println("PRINT ME !!!");

            DeviceMetadata deviceMetadata = new DeviceMetadata();
            deviceMetadata.setUserId(user.getId());
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
            Long userId, String deviceDetails, String location) {
        System.out.println("inside 6");
        List<DeviceMetadata> knownDevices
                = deviceMetadataRepository.findByUserId(userId);

        for (DeviceMetadata existingDevice : knownDevices) {
            if (existingDevice.getDeviceDetails().equals(deviceDetails)
                    && existingDevice.getLocation().equals(location)) {
                return existingDevice;
            }
        }
        return null;
    }

    private String extractIp(HttpServletRequest request) {
        System.out.println("inside 7");
        String clientIp;
        String clientXForwardedForIp = request
                .getHeader("x-forwarded-for");
        if (nonNull(clientXForwardedForIp)) {
            //clientIp = parseXForwardedHeader(clientXForwardedForIp);
            clientIp = clientXForwardedForIp.split(",")[0].trim();
        } else {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    private String getIpLocation(String ip) throws IOException, GeoIp2Exception {
        System.out.println("inside 8");
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
