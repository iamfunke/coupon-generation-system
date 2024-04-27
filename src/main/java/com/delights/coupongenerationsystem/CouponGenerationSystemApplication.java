package com.delights.coupongenerationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "javainuseapi", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class CouponGenerationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponGenerationSystemApplication.class, args);
    }

}
