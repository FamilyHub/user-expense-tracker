package com.example.expenseTracker.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String eventId) {
        super(String.format("Event not found with ID: %s", eventId));
    }
} 