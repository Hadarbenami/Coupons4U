package com.Coupon4.U.exceptions;

public class CustomerExistsException extends Exception{
    public CustomerExistsException() {
        super("Customer already exists");
    }

    public CustomerExistsException(String message) {
        super(message);
    }
}
