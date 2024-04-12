package com.delights.coupongenerationsystem.service;


import com.delights.coupongenerationsystem.dto.request.RetailerCouponRequest;
import com.delights.coupongenerationsystem.dto.response.ApiResponse;
import com.delights.coupongenerationsystem.security.UserPrincipal;

import java.util.concurrent.CompletableFuture;

public interface CouponService {
    CompletableFuture<ApiResponse> generateCoupon(RetailerCouponRequest retailerCouponRequest, UserPrincipal customUserDetails);
    ApiResponse fetchCoupons(UserPrincipal customUserDetails);
}
