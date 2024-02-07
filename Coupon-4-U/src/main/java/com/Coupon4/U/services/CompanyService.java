package com.Coupon4.U.services;

import com.Coupon4.U.exceptions.CouponExistsException;
import com.Coupon4.U.exceptions.IdNotFoundException;
import com.Coupon4.U.models.Category;
import com.Coupon4.U.models.Company;
import com.Coupon4.U.models.Coupon;
import com.Coupon4.U.models.Customer;
import com.Coupon4.U.repositories.CompanyRepository;
import com.Coupon4.U.repositories.CouponRepository;
import com.Coupon4.U.repositories.CustomerRepository;
import com.Coupon4.U.utils.ServiceUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Scope("prototype")
public class CompanyService extends ClientService{

    private int companyId;
    private ApplicationContext context;

    public CompanyService(CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, ApplicationContext context) {
        super(companyRepository, couponRepository, customerRepository);
        this.context = context;
    }

    /**
     * This method creates company object by email and password and check if already exists in DB,
     * if true company id field initialized
     * @param email the company email
     * @param password the company password
     * @return true if the company already exists, else return false
     */
    @Override
    public boolean login(String email, String password) {
        Company company = companyRepository.findByEmailAndPassword(email, password);
        if(company != null){
            this.companyId = company.getId();
            return true;
        }

        return false;
    }


    public Coupon getCouponById(int id) throws IdNotFoundException {
        return couponRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Coupon not found"));
    }

    /**
     * This method checks if coupon already exists by title in the same company, if not added into DB.
     * Cannot add coupon with tha same title in the same company
     * @param coupon to add
     * @throws CouponExistsException thrown if coupon already exists
     */
    public Coupon addCoupon(Coupon coupon) throws CouponExistsException, IdNotFoundException {
        if(getCompanyCoupons().stream().filter(coupon1 -> coupon1.getTitle().equals(coupon.getTitle())).toList().isEmpty()){
            couponRepository.save(coupon);
            return coupon;
        }else {
            throw new CouponExistsException();
        }
    }

    /**
     * This method updates coupon only if exists, company id wasn't change and coupon title doesn't
     * belong to exists company's coupon.
     * @param coupon to update
     * @return true if updated, else returns false
     */
    public void updateCoupon(Coupon coupon) throws CouponExistsException, IdNotFoundException {
        if(couponRepository.existsById(coupon.getId()) && coupon.getCompany().getId() == companyId
                && getCompanyCoupons().stream().filter(coupon1 -> coupon1.getId() != coupon.getId() && coupon1.getTitle().equals(coupon.getTitle())).toList().isEmpty()){
            couponRepository.save(coupon);
        } else{
            throw new CouponExistsException("Invalid update");
        }
    }

    /**
     * This method deletes the history purchases of the coupon, afterward deletes the coupon from coupons table.
     * @param id coupon id to delete from DB
     * @throws IdNotFoundException thrown if coupon wasn't found
     */
    public void deleteCoupon(int id) throws IdNotFoundException {
        context.getBean(ServiceUtils.class).deleteCoupon(id);
    }

    public List<Coupon> getCompanyCoupons() throws IdNotFoundException {
        return getCompany().getCoupons();
    }

    public List<Coupon> getCompanyCouponsByCategory(Category category) throws IdNotFoundException {
        return getCompany().getCoupons().stream().filter(coupon -> coupon.getCategory().equals(category)).toList();
    }

    public List<Coupon> getCompanyCouponsMaxPrice(double price) throws IdNotFoundException {
        return getCompany().getCoupons().stream().filter(coupon -> coupon.getPrice() <= price).toList();
    }

    public Company getCompany() throws IdNotFoundException {
        return companyRepository.findById(companyId).orElseThrow(() -> new IdNotFoundException("Company id not found"));
    }
}
