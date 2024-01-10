package com.Coupon4.U.controllers;

import com.Coupon4.U.exceptions.*;
import com.Coupon4.U.models.Company;
import com.Coupon4.U.models.Customer;
import com.Coupon4.U.services.AdminService;
import com.Coupon4.U.services.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin")

public class AdminController {
    private HttpServletRequest request;
    private HashMap<String, ClientService> tokenStore;


    public AdminController(HttpServletRequest request, HashMap<String, ClientService> tokenStore) {
        this.request = request;
        this.tokenStore = tokenStore;
    }

    //get all
    @GetMapping("/getAllCompanies")
    public List<Company> getAllCompanies() throws UnauthorizedException {
        return getService().getAllCompanies();
    }

    @GetMapping("/getAllCustomers")
    public List<Customer> getAllCustomers() throws UnauthorizedException {
        return getService().getAllCustomers();
    }


    //get one
    @GetMapping("/getOneCompany/{id}")
    public Company getOneCompany(@PathVariable int id) throws IdNotFoundException, UnauthorizedException {
        return getService().getCompanyById(id);
    }

    @GetMapping("/getOneCustomer/{id}")
    public Customer getOneCustomer(@PathVariable int id) throws IdNotFoundException, UnauthorizedException {
        return getService().getCustomerById(id);
    }

    //add
    @PostMapping("/addCompany")
    public ResponseEntity<?> addCompany(@RequestBody Company company) throws CompanyExistsException, UnauthorizedException {
        getService().addCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) throws CustomerExistsException, UnauthorizedException {
        getService().addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer added successfully");
    }

    //update
    @PutMapping("/updateCompany")
    public ResponseEntity<String> updateCompany(@RequestBody Company company) throws CompanyExistsException, IdNotFoundException, UnauthorizedException {
        getService().updateCompany(company);
        return ResponseEntity.ok("Company updated successfully");
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) throws CustomerExistsException, UnauthorizedException {
        getService().updateCustomer(customer);
        return ResponseEntity.ok("Customer updated successfully");
    }


    //delete
    @DeleteMapping("/deleteCompany/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable int id) throws IdNotFoundException, UnauthorizedException {
        if(getService().deleteCompany(id)){
            return ResponseEntity.ok("Company deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company id not found!");
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable int id) throws IdNotFoundException, CustomerExistsException, UnauthorizedException {
        if(getService().deleteCustomer(id)){
            return ResponseEntity.ok("Customer deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer id not found!");
    }

    private AdminService getService() throws UnauthorizedException {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        AdminService service = (AdminService) tokenStore.get(token);
        if(service == null)
            throw new UnauthorizedException("You Have to LOGIN first");
        return service;

    }
}
