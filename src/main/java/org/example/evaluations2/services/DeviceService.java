package org.example.evaluations2.services;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import jakarta.servlet.http.HttpServletRequest;
import org.example.evaluations2.models.DeviceMetadata;
import org.example.evaluations2.models.User;
import org.example.evaluations2.repos.DeviceMetadataRepo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
        //Add Implementation here
        return null;
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

        //Add Implementation here
    }

    private DeviceMetadata findExistingDevice(String userEmail, String deviceDetails, String location) {
         //Add Implementation here
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
        //Add implementation here
        return null;
    }
}
