package com.Coupon4.U.services;

import com.Coupon4.U.clients.ClientType;
import com.Coupon4.U.exceptions.CompanyExistsException;
import com.Coupon4.U.exceptions.CustomerExistsException;
import com.Coupon4.U.exceptions.IdNotFoundException;
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
public class AdminService extends ClientService {

    private ApplicationContext context;

    public AdminService(CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, ApplicationContext context) {
        super(companyRepository, couponRepository, customerRepository);
        this.context = context;
    }

    /**
     * This method checks if email and password equals to admin data
     * @param email to check
     * @param password to cjeck
     * @return true if equals else return false
     */
    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    /**
     * This method checks if company already exists by name and email, if not added into DB
     * @param company to add into companies DB
     * @throws CompanyExistsException thrown if company already exists in DB
     */
    public Company addCompany(Company company) throws CompanyExistsException {
        if(!companyRepository.existsByNameOrEmail(company.getName(), company.getEmail())) {
            companyRepository.save(company);
            return company;

        }else {
            throw new CompanyExistsException();
        }

    }

    /**
     * This method updates company only if already exists, company's name not changed
     * and if updated email doesn't belong to exists company
     * @param company to update
     * @throws IdNotFoundException thrown if company wasn't found
     * @throws CompanyExistsException thrown if company's name changed
     */
    public void updateCompany(Company company) throws IdNotFoundException, CompanyExistsException { // can't update company name
        if(companyRepository.existsById(company.getId()) && getCompanyById(company.getId()).getName().equals(company.getName()) &&
                getAllCompanies().stream().filter(company1 -> company1.getId() != company.getId() && company1.getEmail().equals(company.getEmail())).toList().isEmpty()) {
            companyRepository.save(company);
        } else throw new CompanyExistsException("Invalid update");
    }

    /**
     * This method deletes company's customers purchase, company's coupons and the company itself.
     * @param id company id to delete from DB
     * @throws IdNotFoundException thrown if company wasn't found
     */
    public boolean deleteCompany(int id) throws IdNotFoundException {
        if(companyRepository.existsById(id)) {
            for (Coupon coupon: getCompanyById(id).getCoupons()) {
                context.getBean(ServiceUtils.class).deleteCoupon(coupon.getId()); // delete all company's coupons and coupon's purchase

            }
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public Company getCompanyById(int id) throws IdNotFoundException {
        return companyRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Company id not found!"));
    }

    /**
     * This method checks if customer already exists, if not added into DB.
     * @param customer to add into customers DB
     * @throws CustomerExistsException thrown if customer already exists
     */
    public void addCustomer(Customer customer) throws CustomerExistsException {
        if(customerRepository.findByEmail(customer.getEmail()) != null)
            throw new CustomerExistsException();
        customerRepository.save(customer);
    }
    /**
     * This method updates customer only if already exists and if updated email
     * doesn't belong to exists customer
     * @param customer to update
     * @throws  CustomerExistsException thrown if update isn't valid or customer doesn't exists
     */
    public void updateCustomer(Customer customer) throws CustomerExistsException {
        if(customerRepository.existsById(customer.getId()) &&
                getAllCustomers().stream().filter(customer1 -> customer1.getId() != (customer.getId()) && customer1.getEmail().equals(customer.getEmail())).toList().isEmpty()){
            customerRepository.save(customer);
        }else throw new CustomerExistsException("Invalid update");

    }

    /**
     * This method deletes customer's purchases and the customer from DB
     * @param id customer id to delete
     * @throws IdNotFoundException thrown if customer wasn't found
     *  @throws CustomerExistsException thrown if update isn't valid
     */
    public boolean deleteCustomer(int id) throws IdNotFoundException, CustomerExistsException {
        if(customerRepository.existsById(id)){
            // delete customer purchase
            Customer customer = getCustomerById(id);
            customer.getCoupons().clear();
            updateCustomer(customer);

            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    /**
     * This method returns Customer object by id from DB
     * @param id customer id to return
     * @return customer object
     * @throws IdNotFoundException thrown if customer wasn't found
     */
    public Customer getCustomerById(int id) throws IdNotFoundException {
        return customerRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Customer id not found!"));
    }


}
