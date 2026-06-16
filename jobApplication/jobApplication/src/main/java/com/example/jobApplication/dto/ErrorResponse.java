package com.example.jobApplication.dto;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private String fieldErrors;

    public ErrorResponse() {
    }

    // Parameterized Constructor
    public ErrorResponse(int status, String error, String message, LocalDateTime timestamp, String fieldErrors) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.fieldErrors = fieldErrors;
    }

    // Getters
    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFieldErrors(){
        return fieldErrors;
    }

    // Setters
    public void setStatus(int status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setFieldErrors(String fieldErrors){
        this.fieldErrors = fieldErrors;
    }

}
