package com.Coupon4.U.utils;

import com.Coupon4.U.exceptions.IdNotFoundException;
import com.Coupon4.U.models.Coupon;

import com.Coupon4.U.models.Customer;
import com.Coupon4.U.repositories.CouponRepository;
import com.Coupon4.U.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceUtils {

    private CouponRepository couponRepository;
    private CustomerRepository customerRepository;

    public ServiceUtils(CouponRepository couponRepository, CustomerRepository customerRepository) {
        this.couponRepository = couponRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * This method deletes the history purchases of the coupon, afterward deletes the coupon from coupons table.
     * @param id coupon id to delete from DB
     * @throws IdNotFoundException thrown if coupon wasn't found
     */
    public void deleteCoupon(int id) throws IdNotFoundException {
        Coupon coupon = couponRepository.findById(id).orElseThrow( () -> new IdNotFoundException("Coupon id not found"));
        for(Customer customer : coupon.getCustomers()){
            customer.getCoupons().remove(coupon);
            customerRepository.save(customer);
        }
        couponRepository.deleteById(id);
    }
}
