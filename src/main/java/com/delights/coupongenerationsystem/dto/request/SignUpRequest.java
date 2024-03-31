package com.delights.coupongenerationsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {
    private String fullName;
    private String businessName;
    private String email;
    private String phoneNumber;
    private String password;
    private Set<String> roles;
}
