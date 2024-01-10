package com.Coupon4.U.services;

import com.Coupon4.U.repositories.CompanyRepository;
import com.Coupon4.U.repositories.CouponRepository;
import com.Coupon4.U.repositories.CustomerRepository;

public abstract class ClientService {
    protected CompanyRepository companyRepository;
    protected CouponRepository couponRepository;
    protected CustomerRepository customerRepository;

    protected ClientService(CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository) {
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
        this.customerRepository = customerRepository;
    }

    protected ClientService() {
    }


    abstract public boolean login(String email, String password);

}
