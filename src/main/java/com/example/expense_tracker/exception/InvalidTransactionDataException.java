package com.example.expense_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTransactionDataException extends RuntimeException {
    
    public InvalidTransactionDataException(String message) {
        super(message);
    }

    public InvalidTransactionDataException(String message, Throwable cause) {
        super(message, cause);
    }
} 