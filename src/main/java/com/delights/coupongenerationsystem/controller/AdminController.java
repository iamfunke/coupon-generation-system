package com.delights.coupongenerationsystem.controller;

import com.delights.coupongenerationsystem.dto.response.ApiResponse;
import com.delights.coupongenerationsystem.service.impl.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AdminController {
    private final AdminServiceImpl adminService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("retailers")
    public ResponseEntity<ApiResponse> getAllRetailers() {
        return ResponseEntity.ok().body(adminService.getAllRetailers());
    }
}
