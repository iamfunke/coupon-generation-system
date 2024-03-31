package com.delights.coupongenerationsystem.controller;

import com.delights.coupongenerationsystem.dto.request.RetailerCouponRequest;
import com.delights.coupongenerationsystem.dto.response.ApiResponse;
import com.delights.coupongenerationsystem.security.CurrentUser;
import com.delights.coupongenerationsystem.security.UserPrincipal;
import com.delights.coupongenerationsystem.service.impl.CouponServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class RetailerController {
    private final CouponServiceImpl couponService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RETAILER')")
    @PostMapping("coupons")
    public ResponseEntity<ApiResponse> requestCoupon(
            @CurrentUser UserPrincipal currentUser,
            @RequestBody RetailerCouponRequest retailerCouponRequest) {
        return ResponseEntity.ok().body(couponService.generateCoupon(retailerCouponRequest, currentUser));
    }
}
