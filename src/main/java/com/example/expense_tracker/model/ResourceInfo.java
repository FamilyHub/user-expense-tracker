package com.example.expense_tracker.model;

public class ResourceInfo {
    private String eventId;
    private String eventName;
    private String failureReason;

    // Default constructor
    public ResourceInfo() {
    }

    // All args constructor
    public ResourceInfo(String eventId, String eventName, String failureReason) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.failureReason = failureReason;
    }

    // Getters
    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getFailureReason() {
        return failureReason;
    }

    // Setters
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
} 