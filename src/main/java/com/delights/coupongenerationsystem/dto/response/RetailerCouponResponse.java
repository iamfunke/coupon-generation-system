package com.delights.coupongenerationsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetailerCouponResponse {
    private Long couponId;
    private String couponName;
    private String couponCode;
    private String expiration;
    private Double discount;
    private String createdAt;
}
