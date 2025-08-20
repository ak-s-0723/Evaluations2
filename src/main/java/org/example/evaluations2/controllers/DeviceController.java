package org.example.evaluations2.controllers;

import org.example.evaluations2.models.DeviceMetadata;
import org.example.evaluations2.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/users/{userEmail}")
    public List<DeviceMetadata> getDeviceDetails(@PathVariable String userEmail) {
        return deviceService.findDevicesByUserEmail(userEmail);
    }
}
