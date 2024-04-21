package com.delights.coupongenerationsystem.service.impl;

import com.delights.coupongenerationsystem.dto.response.ApiResponse;
import com.delights.coupongenerationsystem.model.User;
import com.delights.coupongenerationsystem.repository.UserRepository;
import com.delights.coupongenerationsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public ApiResponse getAllRetailers() {
        List<User> retailers = userRepository.findAllRetailers();
        return ApiResponse.builder()
                .success(true)
                .data(retailers)
                .build();
    }

    @Override
    public ApiResponse getSingleRetailer(Long retailerId) {
        return null;
    }

    @Override
    public ApiResponse getAllGeneratedCoupon() {
        return null;
    }

    @Override
    public ApiResponse getAllRetailersCoupon(long retailerId) {
        return null;
    }
}
