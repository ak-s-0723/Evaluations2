package org.example.evaluations2.repos;

import org.example.evaluations2.models.DeviceMetadata;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class DeviceMetadataRepo {
     Map<UUID,DeviceMetadata> deviceMetadataMap;

    public DeviceMetadataRepo() {
        this.deviceMetadataMap = new HashMap<>();
    }

    public DeviceMetadata save(DeviceMetadata deviceMetadata) {
        deviceMetadataMap.put(deviceMetadata.getId(), deviceMetadata);
        return deviceMetadataMap.get(deviceMetadata.getId());
    }

    public List<DeviceMetadata> findByUserEmail(String userEmail) {
        return deviceMetadataMap.values().stream()
                .filter(metadata -> metadata.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());
    }
}
