package com.Coupon4.U.repositories;

import com.Coupon4.U.models.Company;
import com.Coupon4.U.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByEmail(String email);

    Customer findByEmailAndPassword(String email, String password);
}
