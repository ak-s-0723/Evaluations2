package org.example.evaluations2.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto {
    private ResponseStatus status;

    public ResponseDto(ResponseStatus responseStatus) {
        this.status = responseStatus;
    }
}
