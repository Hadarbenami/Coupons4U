package com.Coupon4.U.controllers;

import com.Coupon4.U.clients.ClientType;
import com.Coupon4.U.clients.LoginManager;
import com.Coupon4.U.exceptions.IdNotFoundException;
import com.Coupon4.U.models.Company;
import com.Coupon4.U.models.Customer;
import com.Coupon4.U.models.User;
import com.Coupon4.U.services.ClientService;
import com.Coupon4.U.services.CompanyService;
import com.Coupon4.U.services.CustomerService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")

public class LoginController {

    private LoginManager loginManager;
    private HashMap<String, ClientService> tokenStore;

    //Dependency Injection
    public LoginController(LoginManager loginManager, HashMap<String, ClientService> tokenStore) {
        this.loginManager = loginManager;
        this.tokenStore = tokenStore;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) throws IdNotFoundException {
        ClientService service = loginManager.login(user.getEmail(), user.getPassword(), user.getClientType());

        if (service != null) {

                //login successful
                String token = createToken(service);
                // save token in token store
                tokenStore.put(token, service);
                return ResponseEntity.ok(token);

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        //remove the token from hashmap
        tokenStore.remove(request.getHeader("Authorization").replace("Bearer ", ""));
        return "logout";
    }
    private String createToken(ClientService service) throws IdNotFoundException {
        String token = "";
        if(service instanceof CompanyService){
            Company company = ((CompanyService) service).getCompany();
            Instant expires = Instant.now().plus(30, ChronoUnit.MINUTES);

            token = JWT.create()
                    .withClaim("id", company.getId())
                    .withClaim("name", company.getName())
                    .withClaim("email", company.getEmail())
                    .withClaim("role", "company")
                    .withIssuedAt(new Date())
                    .withExpiresAt(expires)
                    .sign(Algorithm.none());


        } else if(service instanceof CustomerService){
            Customer customer = ((CustomerService) service).getCustomer();
            Instant expires = Instant.now().plus(30, ChronoUnit.MINUTES);

            token = JWT.create()
                    .withClaim("id", customer.getId())
                    .withClaim("firstName", customer.getFirstName())
                    .withClaim("lastName", customer.getLastName())
                    .withClaim("email", customer.getEmail())
                    .withClaim("role", "customer")
                    .withIssuedAt(new Date())
                    .withExpiresAt(expires)
                    .sign(Algorithm.none());



        } else {
            Instant expires = Instant.now().plus(30, ChronoUnit.MINUTES);

            token = JWT.create()
                    .withClaim("role", "admin")
                    .withClaim("name", "Admin" )
                    .withIssuedAt(new Date())
                    .withExpiresAt(expires)
                    .sign(Algorithm.none());


        }

        return token;

    }

}


