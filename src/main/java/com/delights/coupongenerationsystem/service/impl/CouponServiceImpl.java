package com.delights.coupongenerationsystem.service.impl;

import com.delights.coupongenerationsystem.dto.request.RetailerCouponRequest;
import com.delights.coupongenerationsystem.dto.response.ApiResponse;
import com.delights.coupongenerationsystem.dto.response.RetailerCouponResponse;
import com.delights.coupongenerationsystem.exception.AppException;
import com.delights.coupongenerationsystem.model.Coupon;
import com.delights.coupongenerationsystem.model.User;
import com.delights.coupongenerationsystem.repository.CouponRepository;
import com.delights.coupongenerationsystem.repository.UserRepository;
import com.delights.coupongenerationsystem.security.UserPrincipal;
import com.delights.coupongenerationsystem.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public CompletableFuture<ApiResponse> generateCoupon(RetailerCouponRequest retailerCouponRequest, UserPrincipal customUserDetails) {
       ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        return CompletableFuture.supplyAsync(() -> {
            try {

                LocalDateTime expirationDate = retailerCouponRequest.getExpiration();
                LocalDateTime now = LocalDateTime.now();
                if (expirationDate.isBefore(now)) {
                    throw new IllegalArgumentException("Expiration date must be in the future");
                }

                String tempCouponCode = UUID.randomUUID().toString().replaceAll("-", "");
                String couponCode = tempCouponCode.substring(0, 8);

                User loggedInRetailer = userRepository.findById(customUserDetails.getId())
                        .orElseThrow(() -> new AppException("Retailer doesn't exist"));

                // Build the data to be stored in the database
                Coupon couponObject = Coupon.builder()
                        .couponName(retailerCouponRequest.getCouponName())
                        .discount(retailerCouponRequest.getDiscount())
                        .expiration(retailerCouponRequest.getExpiration())
                        .couponCode(couponCode)
                        .retailer(loggedInRetailer)
                        .build();

                // Storing in DB
                Coupon newCouponObject = couponRepository.save(couponObject);
                // Structuring response
                RetailerCouponResponse retailerCouponResponse = RetailerCouponResponse.builder()
                        .couponId(newCouponObject.getId())
                        .couponCode(newCouponObject.getCouponCode())
                        .couponName(newCouponObject.getCouponName())
                        .discount(newCouponObject.getDiscount())
                        .expiration(newCouponObject.getExpiration().toString())
                        .createdAt(newCouponObject.getCreatedAt().toString())
                        .build();

                ApiResponse apiResponse = ApiResponse.builder()
                        .success(true)
                        .message("Coupon generated Successfully")
                        .data(retailerCouponResponse)
                        .build();

                return apiResponse; // Return the ApiResponse
            } catch (Exception ex) {
                // Handle exception and return an error response
                return ApiResponse.builder()
                        .success(false)
                        .message("Coupon generation failed: " + ex.getMessage())
                        .build();
            }
        }, executorService).exceptionally(ex -> {
            // Handle exception and return an error response
            return ApiResponse.builder()
                    .success(false)
                    .message("Coupon generation failed: " + ex.getMessage())
                    .build();
        });
    }

    @Override
    public ApiResponse fetchCoupons(UserPrincipal customUserDetails) {
        User loggedInRetailer = userRepository.findById(customUserDetails.getId())
                .orElseThrow(() -> new AppException("Retailer doesn't exist"));

        List<Coupon> coupons = couponRepository.findByRetailerId(loggedInRetailer.getId());
        List<RetailerCouponResponse> response = coupons.stream().map(this::retailerCouponResponse).collect(Collectors.toList());

       return ApiResponse.builder()
                .success(true)
                .data(response)
                .build();

    }

    private RetailerCouponResponse retailerCouponResponse(Coupon coupon){
        return RetailerCouponResponse.builder()
                .couponId(coupon.getId())
                .couponCode(coupon.getCouponCode())
                .couponName(coupon.getCouponName())
                .discount(coupon.getDiscount())
                .expiration(coupon.getExpiration().toString())
                .createdAt(coupon.getCreatedAt().toString())
                .build();
    }
}
