package com.delights.coupongenerationsystem.model;

import com.delights.coupongenerationsystem.model.audit.DateAudit;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "coupons", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "couponCode"
        })
})
public class Coupon extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String couponName;
    private String couponCode;
    private LocalDateTime expiration;
    private double discount;

    @ManyToOne
    @JoinColumn(columnDefinition = "user_id")
    private User retailer;
}
