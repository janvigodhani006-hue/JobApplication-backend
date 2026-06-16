package com.example.jobApplication.exception;

public class ResponseNotFoundException extends RuntimeException {
    public ResponseNotFoundException(String message) {
        super(message);
    }
}
