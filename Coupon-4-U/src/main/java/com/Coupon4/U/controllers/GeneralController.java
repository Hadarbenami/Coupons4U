package com.Coupon4.U.controllers;

import com.Coupon4.U.exceptions.IdNotFoundException;
import com.Coupon4.U.exceptions.UnauthorizedException;
import com.Coupon4.U.models.Coupon;
import com.Coupon4.U.services.CompanyService;
import com.Coupon4.U.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general")

public class GeneralController {

    private CustomerService customerService;
    private CompanyService companyService;


    public GeneralController(CustomerService customerService, CompanyService companyService) {
        this.customerService = customerService;
        this.companyService = companyService;
    }

    @GetMapping("/getAllCoupons")
    public List<Coupon> getAllCoupons() {
        return customerService.getAllCoupons();
    }

    @GetMapping("/getOneCoupon/{id}")
    public Coupon getOneCoupon(@PathVariable int id) throws UnauthorizedException, IdNotFoundException {
        return companyService.getCouponById(id);
    }

}
