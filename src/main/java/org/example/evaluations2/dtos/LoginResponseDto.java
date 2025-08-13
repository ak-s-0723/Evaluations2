package org.example.evaluations2.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponseDto {
    private LoginStatus status;
    private String token;
}
