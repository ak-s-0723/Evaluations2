package org.example.evaluations2.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
public class DeviceMetadata {
    private UUID id;
    private String userEmail;
    private String deviceDetails;
    private String location;
    private Date lastLoggedIn;
}
