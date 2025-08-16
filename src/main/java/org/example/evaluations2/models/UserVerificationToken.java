package org.example.evaluations2.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


@Setter
@Getter
public class UserVerificationToken {
    private static final int EXPIRATION = 60 * 24;

    private UUID id;

    private String token;

    private User user;

    private Date expiryDate;

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

    public UserVerificationToken() {
        id = UUID.randomUUID();
        expiryDate = calculateExpiryDate();
    }
}
