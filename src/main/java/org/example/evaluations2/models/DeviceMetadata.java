package org.example.evaluations2.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class DeviceMetadata {
    private Long id;
    private Long userId;
    private String deviceDetails;
    private String location;
    private Date lastLoggedIn;
}
