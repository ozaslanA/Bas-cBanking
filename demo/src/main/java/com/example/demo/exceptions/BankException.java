package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class BankException extends RuntimeException{
    private HttpStatus status;

    public BankException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
