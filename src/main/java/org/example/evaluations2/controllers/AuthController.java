package org.example.evaluations2.controllers;

import io.jsonwebtoken.JwtException;
import org.example.evaluations2.dtos.ResponseDto;
import org.example.evaluations2.dtos.ResponseStatus;
import org.example.evaluations2.services.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private ITokenService tokenService;

    @GetMapping("/validateToken")
    public ResponseEntity<ResponseDto> validateAuthToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            tokenService.validateToken(token);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(ResponseStatus.SUCCESS,""));
        } catch (JwtException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDto(ResponseStatus.FAILURE,exception.getMessage()));
        }
    }
}
