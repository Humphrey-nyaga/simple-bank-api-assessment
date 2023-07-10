package com.example.simplebankapi.exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException( String message) {
        super(message);
    }
}
