package com.Coupon4.U.services;

import com.Coupon4.U.exceptions.IdNotFoundException;
import com.Coupon4.U.exceptions.PurchaseException;
import com.Coupon4.U.models.Category;
import com.Coupon4.U.models.Coupon;
import com.Coupon4.U.models.Customer;
import com.Coupon4.U.repositories.CompanyRepository;
import com.Coupon4.U.repositories.CouponRepository;
import com.Coupon4.U.repositories.CustomerRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
public class CustomerService extends ClientService{

    private int customerId;

    private ApplicationContext context;

    public CustomerService(CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, ApplicationContext context) {
        super(companyRepository, couponRepository, customerRepository);
        this.context = context;
    }

    /**
     * This method creates customer object by email and password and check if already exists in DB,
     * if true customer id field initialized
     * @param email the customer email
     * @param password the customer password
     * @return true if the customer already exists, else return false
     */
    @Override
    public boolean login(String email, String password) {
        Customer customer = customerRepository.findByEmailAndPassword(email, password);
        if(customer != null){
            this.customerId = customer.getId();
            return true;
        }

        return false;
    }

    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }

    /**
     * This method checks if customer hasn't purchased the coupon, end date hasn't past and if the amount bigger than zero.
     * If true the purchase approve.
     * if purchase approved coupon added into customer purchases and coupon's amount reduce by purchase's amount.
     * @param coupon coupon to purchase
     * @throws IdNotFoundException thrown if coupon wasn't found.
     * @throws PurchaseException thrown if coupon isn't valid to purchase by the above-mentioned.
     */
    public void purchaseCoupon(Coupon coupon) throws IdNotFoundException, PurchaseException {
        if(getCustomerCoupons().stream().filter(coupon1 -> coupon1.equals(coupon)).toList().isEmpty()){
            LocalDate currentDate = LocalDate.now();
            if(coupon.getAmount() > 0 && coupon.getEndDate().isAfter(currentDate) || coupon.getEndDate().isEqual(currentDate) ){

                Customer customer = getCustomer();
                customer.getCoupons().add(coupon);
                customerRepository.save(customer);

                coupon.setAmount(coupon.getAmount() - 1);
                couponRepository.save(coupon);

            } else throw new PurchaseException("Invalid coupon!");
        } else throw new PurchaseException("You already purchase this coupon...");
    }

    public Set<Coupon> getCustomerCoupons() throws IdNotFoundException {
        return getCustomer().getCoupons();
    }

    public Set<Coupon> getCouponsByCategory(Category category) throws IdNotFoundException {
        return getCustomer().getCoupons().stream().filter(coupon -> coupon.getCategory().equals(category)).collect(Collectors.toSet());
    }

    public Set<Coupon> getCouponsByMaxPrice(double price) throws IdNotFoundException {
        return getCustomer().getCoupons().stream().filter(coupon -> coupon.getPrice() <= price).collect(Collectors.toSet());
    }

    public Customer getCustomer() throws IdNotFoundException {
        return customerRepository.findById(customerId).orElseThrow(() -> new IdNotFoundException("Customer id not found"));
    }
}
