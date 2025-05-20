package com.example.expenseTracker.exception;

public class InvalidEventDataException extends RuntimeException {
    public InvalidEventDataException(String message) {
        super(message);
    }
} 