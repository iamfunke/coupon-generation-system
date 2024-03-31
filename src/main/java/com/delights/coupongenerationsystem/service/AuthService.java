package com.delights.coupongenerationsystem.service;


import com.delights.coupongenerationsystem.dto.request.LoginRequest;
import com.delights.coupongenerationsystem.dto.request.SignUpRequest;
import com.delights.coupongenerationsystem.dto.response.ApiResponse;

public interface AuthService {
    ApiResponse createUser(SignUpRequest signUpRequest);
    ApiResponse authenticateUser(LoginRequest loginRequest);
}
