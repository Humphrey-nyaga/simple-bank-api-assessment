package com.example.simplebankapi.Exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException( String message) {
        super(message);
    }
}
