package com.example.expenseTracker.dto;

import java.util.List;

public class EventStatusResponseDTO {
    private List<String> completedEventIds;
    private List<String> pendingEventIds;

    // Default constructor
    public EventStatusResponseDTO() {
    }

    // All args constructor
    public EventStatusResponseDTO(List<String> completedEventIds, List<String> pendingEventIds) {
        this.completedEventIds = completedEventIds;
        this.pendingEventIds = pendingEventIds;
    }

    // Getters
    public List<String> getCompletedEventIds() {
        return completedEventIds;
    }

    public List<String> getPendingEventIds() {
        return pendingEventIds;
    }

    // Setters
    public void setCompletedEventIds(List<String> completedEventIds) {
        this.completedEventIds = completedEventIds;
    }

    public void setPendingEventIds(List<String> pendingEventIds) {
        this.pendingEventIds = pendingEventIds;
    }
} 