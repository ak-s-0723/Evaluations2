package org.example.evaluations2.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto {
    private ResponseStatus status;
    private String message;

    public ResponseDto(ResponseStatus responseStatus,String message) {
        this.status = responseStatus;
        this.message = message;
    }
}
