package com.Coupon4.U.clients;

import com.Coupon4.U.exceptions.IdNotFoundException;
import com.Coupon4.U.exceptions.UnauthorizedException;
import com.Coupon4.U.services.AdminService;
import com.Coupon4.U.services.ClientService;
import com.Coupon4.U.services.CompanyService;
import com.Coupon4.U.services.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {
    private ApplicationContext context;


    public LoginManager(ApplicationContext context) {
        this.context = context;
    }

    public ClientService login(String email, String password, ClientType clientType){

        switch (clientType) {
            case ADMINISTRATOR:
                AdminService admin = context.getBean(AdminService.class);
                if (admin.login(email, password))
                    return admin;
                break;
            case COMPANY:
                CompanyService company = context.getBean(CompanyService.class);
                if (company.login(email, password))
                    return company;
                break;
            case CUSTOMER:
                CustomerService customer = context.getBean(CustomerService.class);
                if (customer.login(email, password))
                    return customer;
                break;
        }
        return null;
    }
}


