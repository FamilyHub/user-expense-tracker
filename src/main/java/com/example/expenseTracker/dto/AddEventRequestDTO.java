package com.example.expenseTracker.dto;

import com.example.expenseTracker.model.CustomField;

import java.util.List;

public class AddEventRequestDTO {
    private String eventName;
    private long eventDate;
    private List<CustomField> customFields;
    private boolean isAllowNotification;
    private boolean isAllowToSeeThisEvent;
    private boolean isEventCompleted;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getEventDate() {
        return eventDate;
    }

    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }

    public List<CustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
    }

    public boolean isAllowNotification() {
        return isAllowNotification;
    }

    public void setAllowNotification(boolean allowNotification) {
        isAllowNotification = allowNotification;
    }

    public boolean isAllowToSeeThisEvent() {
        return isAllowToSeeThisEvent;
    }

    public void setAllowToSeeThisEvent(boolean allowToSeeThisEvent) {
        isAllowToSeeThisEvent = allowToSeeThisEvent;
    }

    public boolean isEventCompleted() {
        return isEventCompleted;
    }

    public void setEventCompleted(boolean eventCompleted) {
        isEventCompleted = eventCompleted;
    }
} 