package org.example.evaluations2.controllers;

import org.example.evaluations2.dtos.RequestDto;
import org.example.evaluations2.dtos.ResponseDto;
import org.example.evaluations2.dtos.ResponseStatus;
import org.example.evaluations2.services.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private ITokenService tokenService;

    @PostMapping("/authToken")
    public ResponseEntity<ResponseDto> generateAuthToken(@RequestBody RequestDto requestDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            String token = tokenService.generateJwt(requestDto.getUserId());
            responseDto.setStatus(ResponseStatus.SUCCESS);
            MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
            headers.add(HttpHeaders.SET_COOKIE,token);
            return new ResponseEntity<>(responseDto,headers, HttpStatus.CREATED);
        } catch (RuntimeException exception) {
            responseDto.setStatus(ResponseStatus.FAILURE);
            return new ResponseEntity<>(responseDto,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
