package com.example.expense_tracker.model;

import java.util.List;

public class BulkEventResponse {
    private List<AddEvent> successList;
    private List<ResourceInfo> failureList;

    // Default constructor
    public BulkEventResponse() {
    }

    // All args constructor
    public BulkEventResponse(List<AddEvent> successList, List<ResourceInfo> failureList) {
        this.successList = successList;
        this.failureList = failureList;
    }

    // Getters
    public List<AddEvent> getSuccessList() {
        return successList;
    }

    public List<ResourceInfo> getFailureList() {
        return failureList;
    }

    // Setters
    public void setSuccessList(List<AddEvent> successList) {
        this.successList = successList;
    }

    public void setFailureList(List<ResourceInfo> failureList) {
        this.failureList = failureList;
    }
} 