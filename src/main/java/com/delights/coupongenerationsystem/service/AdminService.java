package com.delights.coupongenerationsystem.service;

import com.delights.coupongenerationsystem.dto.response.ApiResponse;

public interface AdminService {
    ApiResponse getAllRetailers();
    ApiResponse getSingleRetailer(Long retailerId);
    ApiResponse getAllGeneratedCoupon();
    ApiResponse getAllRetailersCoupon(long retailerId);
}
