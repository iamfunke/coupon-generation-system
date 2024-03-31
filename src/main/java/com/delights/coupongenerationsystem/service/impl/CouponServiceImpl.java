package com.delights.coupongenerationsystem.service.impl;

import com.delights.coupongenerationsystem.dto.request.RetailerCouponRequest;
import com.delights.coupongenerationsystem.dto.response.ApiResponse;
import com.delights.coupongenerationsystem.dto.response.RetailerCouponResponse;
import com.delights.coupongenerationsystem.exception.AppException;
import com.delights.coupongenerationsystem.exception.ResourceNotFoundException;
import com.delights.coupongenerationsystem.model.Coupon;
import com.delights.coupongenerationsystem.model.User;
import com.delights.coupongenerationsystem.repository.CouponRepository;
import com.delights.coupongenerationsystem.repository.UserRepository;
import com.delights.coupongenerationsystem.security.UserPrincipal;
import com.delights.coupongenerationsystem.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse generateCoupon(RetailerCouponRequest retailerCouponRequest, UserPrincipal customUserDetails) {
        // Random alphanumeric
        // Random UUID
        // "02364d17-6379-4ee2-b3e9-c9eecd352614"
        // 02364d1763794ee2b3e9c9eecd352614
        String tempCouponCode = UUID.randomUUID().toString().replaceAll("-", "");
        String couponCode = tempCouponCode.substring(0, 8);

        User loggedInRetailer = userRepository.findById(customUserDetails.getId())
                .orElseThrow(()->new AppException("Retailer doesn't exist"));

        // Build the data to be persisted/stored in database
        Coupon couponObject = Coupon.builder()
                .couponName(retailerCouponRequest.getCouponName())
                .discount(retailerCouponRequest.getDiscount())
                .expiration(retailerCouponRequest.getExpiration())
                .couponCode(couponCode)
                .retailer(loggedInRetailer)
                .build();

        Coupon newCouponObject = couponRepository.save(couponObject);
        RetailerCouponResponse retailerCouponResponse = RetailerCouponResponse.builder()
                .couponId(newCouponObject.getId())
                .couponCode(newCouponObject.getCouponCode())
                .couponName(newCouponObject.getCouponName())
                .discount(newCouponObject.getDiscount())
                .expiration(newCouponObject.getExpiration().toString())
                .createdAt(newCouponObject.getCreatedAt().toString())
                .build();

        return ApiResponse.builder()
                .success(true)
                .message("Coupon generated Successfully")
                .data(retailerCouponResponse)
                .build();
    }
}
