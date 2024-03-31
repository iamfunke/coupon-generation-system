package com.delights.coupongenerationsystem.service.impl;


import com.delights.coupongenerationsystem.dto.request.LoginRequest;
import com.delights.coupongenerationsystem.dto.request.SignUpRequest;
import com.delights.coupongenerationsystem.dto.response.ApiResponse;
import com.delights.coupongenerationsystem.exception.AppException;
import com.delights.coupongenerationsystem.dto.response.UserLoginResponse;
import com.delights.coupongenerationsystem.exception.BadRequestException;
import com.delights.coupongenerationsystem.exception.InvalidCredentialsException;
import com.delights.coupongenerationsystem.exception.ResourceNotFoundException;
import com.delights.coupongenerationsystem.model.Role;
import com.delights.coupongenerationsystem.model.RoleName;
import com.delights.coupongenerationsystem.model.User;
import com.delights.coupongenerationsystem.repository.RoleRepository;
import com.delights.coupongenerationsystem.repository.UserRepository;
import com.delights.coupongenerationsystem.security.JwtTokenProvider;
import com.delights.coupongenerationsystem.security.UserPrincipal;
import com.delights.coupongenerationsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    public ApiResponse authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new ResourceNotFoundException("User ", "email", loginRequest.getEmail()));
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            final String token = tokenProvider.generateToken(authentication);
            UserLoginResponse userInfoResponse = UserLoginResponse.builder()
                    .id(userDetails.getId())
                    .fullname(userDetails.getUsername())
                    .email(userDetails.getEmail())
                    .token(token)
                    .roles(roles.stream().collect(Collectors.toSet()))
                    .token(tokenProvider.generateToken(authentication))
                    .build();


            return ApiResponse.builder()
                    .success(true)
                    .message("Login Successful.")
                    .data(userInfoResponse)
                    .build();

        }catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid login credentials");
        }
    }

    @Override
    public ApiResponse createUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email Address already in use!");
        }

        // Creating user's account
        User user = User.builder()
                .fullName(signUpRequest.getFullName())
                .businessName(signUpRequest.getBusinessName())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .build();

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_RETAILER)
                    .orElseThrow(() -> new AppException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new AppException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleName.ROLE_RETAILER)
                                .orElseThrow(() -> new AppException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }


        user.setRoles(roles);
        User userObject = userRepository.save(user);

        return ApiResponse.builder()
                .success(true)
                .message("User registered successfully")
                .data(userObject)
                .build();
    }
}
