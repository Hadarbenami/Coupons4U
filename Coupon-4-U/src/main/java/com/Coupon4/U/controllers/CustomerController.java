package com.Coupon4.U.controllers;

import com.Coupon4.U.exceptions.IdNotFoundException;
import com.Coupon4.U.exceptions.PurchaseException;
import com.Coupon4.U.exceptions.UnauthorizedException;
import com.Coupon4.U.models.Category;
import com.Coupon4.U.models.Coupon;
import com.Coupon4.U.services.ClientService;
import com.Coupon4.U.services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/customer")

public class CustomerController {
    private HttpServletRequest request;
    private HashMap<String, ClientService> tokenStore;


    public CustomerController(HttpServletRequest request, HashMap<String, ClientService> tokenStore) {
        this.request = request;
        this.tokenStore = tokenStore;
    }

    //get
    @GetMapping("/getAllCoupons")
    public List<Coupon> getAllCoupons() throws UnauthorizedException {
        return getService().getAllCoupons();
    }

    @GetMapping("/getMyCoupons")
    public Set<Coupon> getCustomerCoupons() throws IdNotFoundException, UnauthorizedException {
        return getService().getCustomerCoupons();
    }

    @GetMapping("/getCouponsByCategory/{category}")
    public Set<Coupon> getCouponsByCategory(Category category) throws IdNotFoundException, UnauthorizedException {
        return getService().getCouponsByCategory(category);
    }

    @GetMapping("/getCouponsByMaxPrice/{price}")
    public Set<Coupon> getCouponsByMaxPrice(double price) throws IdNotFoundException, UnauthorizedException {
        return getService().getCouponsByMaxPrice(price);
    }

    //purchase
    @PostMapping("/purchaseCoupon")
    public ResponseEntity<String> purchaseCoupon(@RequestBody Coupon coupon) throws PurchaseException, IdNotFoundException, UnauthorizedException {
        getService().purchaseCoupon(coupon);
        return ResponseEntity.ok("Coupon purchase successfully");

    }

    private CustomerService getService() throws UnauthorizedException {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        CustomerService service = (CustomerService) tokenStore.get(token);
        if(service == null)
            throw new UnauthorizedException("You Have to LOGIN first");
        return service;

    }

}
