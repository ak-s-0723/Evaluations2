package org.example.evaluations2.controllers;

import org.example.evaluations2.dtos.RequestDto;
import org.example.evaluations2.dtos.ResponseDto;
import org.example.evaluations2.dtos.ResponseStatus;
import org.example.evaluations2.services.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private ITokenService tokenService;

    @PostMapping("/authToken")
    public ResponseEntity<ResponseDto> generateAuthToken(@RequestBody RequestDto requestDto) {
        try {
            String token = tokenService.generateJwt(requestDto.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(new ResponseDto(ResponseStatus.SUCCESS));
        } catch (RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ResponseStatus.FAILURE));
        }
    }
}
