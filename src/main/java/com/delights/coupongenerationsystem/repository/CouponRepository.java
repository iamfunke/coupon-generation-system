package com.delights.coupongenerationsystem.repository;

import com.delights.coupongenerationsystem.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCouponCode(String couponCode);
    List<Coupon> findByRetailerId(Long id);

    Coupon findByIdAndRetailerId(Long couponId, Long retailerId);
}
