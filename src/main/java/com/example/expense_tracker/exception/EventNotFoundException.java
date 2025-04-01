package com.example.expense_tracker.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String eventId) {
        super(String.format("Event not found with ID: %s", eventId));
    }
} 