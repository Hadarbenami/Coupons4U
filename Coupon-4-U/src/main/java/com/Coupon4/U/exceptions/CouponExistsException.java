package com.Coupon4.U.exceptions;

public class CouponExistsException extends Exception{
    public CouponExistsException() {
        super("Coupon already exists");
    }

    public CouponExistsException(String message) {
        super(message);
    }
}
