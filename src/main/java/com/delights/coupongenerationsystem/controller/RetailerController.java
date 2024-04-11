package com.delights.coupongenerationsystem.controller;

import com.delights.coupongenerationsystem.dto.request.RetailerCouponRequest;
import com.delights.coupongenerationsystem.dto.response.ApiResponse;
import com.delights.coupongenerationsystem.security.CurrentUser;
import com.delights.coupongenerationsystem.security.UserPrincipal;
import com.delights.coupongenerationsystem.service.impl.CouponServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class RetailerController {
    private final CouponServiceImpl couponService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RETAILER')")
    @PostMapping("coupons")
    public CompletableFuture<ResponseEntity<ApiResponse>> requestCoupon(
            @CurrentUser UserPrincipal currentUser,
            @RequestBody RetailerCouponRequest retailerCouponRequest) {
        return couponService.generateCoupon(retailerCouponRequest, currentUser)
                .thenApply(apiResponse -> ResponseEntity.ok().body(apiResponse))
                .exceptionally(ex -> {
                    // Handle exception and return an error response
                    ApiResponse errorResponse = ApiResponse.builder()
                            .success(false)
                            .message("Coupon generation failed: " + ex.getMessage())
                            .build();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                });
    }

}
