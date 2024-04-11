package com.delights.coupongenerationsystem.dto.request;

import com.delights.coupongenerationsystem.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetailerCouponRequest {
    private String couponName;
    private LocalDateTime expiration;
    private double discount;
}
