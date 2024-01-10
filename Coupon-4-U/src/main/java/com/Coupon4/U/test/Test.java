package com.Coupon4.U.test;

import com.Coupon4.U.clients.ClientType;
import com.Coupon4.U.clients.LoginManager;
import com.Coupon4.U.exceptions.*;
import com.Coupon4.U.models.Category;
import com.Coupon4.U.models.Company;
import com.Coupon4.U.models.Coupon;
import com.Coupon4.U.models.Customer;
import com.Coupon4.U.services.AdminService;
import com.Coupon4.U.services.CompanyService;
import com.Coupon4.U.services.CustomerService;
import com.Coupon4.U.thread.CouponExpiredDalyJob;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;

@Service
public class Test {

    private ApplicationContext context;

    public Test(ApplicationContext context) {
        this.context = context;
    }

    public void testAll() {
        try {

            CouponExpiredDalyJob job = context.getBean(CouponExpiredDalyJob.class);
            //job.deleteExpiredCoupons();

           //testAdmin();
            //testCompany();
            //testCustomer();


//

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    public void testAdmin() throws SQLException, CompanyExistsException, CustomerExistsException, IdNotFoundException {
        LoginManager loginManager = context.getBean(LoginManager.class);
        AdminService admin = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
       //Company company = new Company("Danon", "danon@co.il", "8888", null);
      //admin.addCompany(company);
     //Company newCompany = new Company(5,"Danon", "danon@co.il", "8888", null);
     //admin.updateCompany(newCompany);
      //System.out.println(admin.getAllCompanies());
      //System.out.println(admin.getCompanyById(5));
      //admin.deleteCompany(5);

       //Customer customer = new Customer("Ori", "Tzur", "ori@gmail.com", "5555", null);
        //admin.addCustomer(customer);
      //Customer newCustomer = new Customer(7,"Ori", "Tzur", "ori5@gmail.com", "9999", null);
      //admin.updateCustomer(newCustomer);
      //System.out.println(admin.getAllCustomers());
      //System.out.println(admin.getCustomerById(6));
       //admin.deleteCustomer(3);


    }

    public void testCompany() throws CouponExistsException, IdNotFoundException {
        LoginManager loginManager = context.getBean(LoginManager.class);
        CompanyService company = (CompanyService) loginManager.login("h&m@co.il", "1234", ClientType.COMPANY);
        Company company1 = new Company(1,"H&M", "h&m@co.il", "1234", null);
       //company.addCoupon(new Coupon( company1,Category.JEWELRY, "5%", "old items", LocalDate.of(2023,12, 3), LocalDate.of(2023, 12, 10), 5, 23.99, ""));
        //Coupon update = new Coupon(12, company1, Category.JEWELRY, "buy 1 get 1 for free", "blue friday", LocalDate.of(2023,11, 10), LocalDate.of(2023, 12, 3), 20, 40.99, "", null);
        //company.updateCoupon(update);
        //System.out.println(company.getCompanyCoupons());
        //System.out.println(company.getCompanyCouponsByCategory(Category.CLOTHING));
        //System.out.println(company.getCompanyCouponsMaxPrice(38));
        //System.out.println(company.getCompany());
      //company.deleteCoupon(12);
    }

    public void testCustomer() throws PurchaseException, IdNotFoundException {
        LoginManager loginManager = context.getBean(LoginManager.class);
        CustomerService customer = (CustomerService) loginManager.login("hadar@gmail.com", "1414", ClientType.CUSTOMER );

        Company company1 = new Company(5,"Danon", "danon@co.il", "5678", null);
        //customer.purchaseCoupon(new Coupon(16, company1,Category.JEWELRY, "5%", "old items", LocalDate.of(2023,12, 3), LocalDate.of(2023, 12, 10), 3, 23.99, "", null));
        //System.out.println(customer.getCustomer());
        //System.out.println(customer.getCustomerCoupons());
        //System.out.println(customer.getCouponsByCategory(Category.VACATION));
        //System.out.println(customer.getCouponsByMaxPrice(54));
    }
}

