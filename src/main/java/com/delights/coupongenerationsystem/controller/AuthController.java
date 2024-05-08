package com.delights.coupongenerationsystem.controller;

import com.delights.coupongenerationsystem.dto.request.LoginRequest;
import com.delights.coupongenerationsystem.dto.request.SignUpRequest;
import com.delights.coupongenerationsystem.dto.response.ApiResponse;
import com.delights.coupongenerationsystem.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/")
public class AuthController {

    private final AuthServiceImpl userService;

    @PostMapping("login")
    public ResponseEntity<ApiResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok().body(userService.authenticateUser(loginRequest));
        }catch (Exception ex){
            ApiResponse errorResponse = ApiResponse.builder()
                    .success(false)
                    .message("Invalid Login.")
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

                        @PostMapping("register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody SignUpRequest signUpRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(signUpRequest));
        } catch (Exception ex){
            ApiResponse errorResponse = ApiResponse.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
