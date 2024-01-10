package com.Coupon4.U.repositories;

import com.Coupon4.U.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
    boolean existsByNameOrEmail(String name, String email);
    Company findByEmailAndPassword(String email, String password);


}
