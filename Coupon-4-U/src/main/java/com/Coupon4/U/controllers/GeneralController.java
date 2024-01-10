package com.Coupon4.U.controllers;

import com.Coupon4.U.exceptions.UnauthorizedException;
import com.Coupon4.U.models.Coupon;
import com.Coupon4.U.services.CustomerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/general")

public class GeneralController {

    private CustomerService service;


    public GeneralController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/getAllCoupons")
    public List<Coupon> getAllCoupons() {
        return service.getAllCoupons();
    }
}
