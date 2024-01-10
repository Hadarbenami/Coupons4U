package com.Coupon4.U.exceptions;

public class CompanyExistsException extends Exception{
    public CompanyExistsException() {
        super("Company already exists");
    }

    public CompanyExistsException(String message) {
        super(message);
    }
}
