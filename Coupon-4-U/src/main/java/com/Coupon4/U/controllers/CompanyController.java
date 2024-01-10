package com.Coupon4.U.controllers;

import com.Coupon4.U.exceptions.CouponExistsException;
import com.Coupon4.U.exceptions.IdNotFoundException;
import com.Coupon4.U.exceptions.UnauthorizedException;
import com.Coupon4.U.models.Category;
import com.Coupon4.U.models.Company;
import com.Coupon4.U.models.Coupon;
import com.Coupon4.U.services.ClientService;
import com.Coupon4.U.services.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/company")

public class CompanyController {

    private HttpServletRequest request;
    private HashMap<String, ClientService> tokenStore;

    public CompanyController(HttpServletRequest request, HashMap<String, ClientService> tokenStore) {
        this.request = request;
        this.tokenStore = tokenStore;
    }

    //get
    @GetMapping("/getCompanyDetails")
    public Company getCompany() throws IdNotFoundException, UnauthorizedException {
        return getService().getCompany();
    }

    @GetMapping("/getCompanyCoupons")
    public List<Coupon> getCompanyCoupons() throws IdNotFoundException, UnauthorizedException {
        return getService().getCompanyCoupons();
    }

    @GetMapping("/getCouponsByCategory/{category}")
    public List<Coupon> getCouponsByCategory(@PathVariable Category category) throws IdNotFoundException, UnauthorizedException {
        return getService().getCompanyCouponsByCategory(category);
    }

    @GetMapping("/getCouponsByMaxPrice/{price}")
    public List<Coupon> getCouponsByMaxPrice(@PathVariable double price) throws IdNotFoundException, UnauthorizedException {
        return getService().getCompanyCouponsMaxPrice(price);
    }

    //add
    @PostMapping("/addCoupon")
    public ResponseEntity<String> addCoupon(@RequestBody Coupon coupon) throws CouponExistsException, IdNotFoundException, UnauthorizedException {
        getService().addCoupon(coupon);
        return ResponseEntity.status(HttpStatus.CREATED).body("Coupon added successfully");
    }

    //update
    @PutMapping("/updateCoupon")
    public ResponseEntity<String> updateCoupon(@RequestBody Coupon coupon) throws CouponExistsException, IdNotFoundException, UnauthorizedException {
        getService().updateCoupon(coupon);
        return ResponseEntity.ok("Coupon updated successfully");
    }

    //delete
    @DeleteMapping("/deleteCoupon/{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable int id) throws IdNotFoundException, UnauthorizedException {
        getService().deleteCoupon(id);
        return ResponseEntity.ok("Coupon deleted successfully");
    }

    private CompanyService getService() throws UnauthorizedException {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        CompanyService service = (CompanyService) tokenStore.get(token);
        if(service == null)
            throw new UnauthorizedException("You Have to LOGIN first");
        return service;

    }


}
