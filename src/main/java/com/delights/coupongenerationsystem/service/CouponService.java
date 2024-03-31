package com.delights.coupongenerationsystem.service;


import com.delights.coupongenerationsystem.dto.request.RetailerCouponRequest;
import com.delights.coupongenerationsystem.dto.response.ApiResponse;
import com.delights.coupongenerationsystem.security.UserPrincipal;

public interface CouponService {
    ApiResponse generateCoupon(RetailerCouponRequest retailerCouponRequest, UserPrincipal customUserDetails);
}
