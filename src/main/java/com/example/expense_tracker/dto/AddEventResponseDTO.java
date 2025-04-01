package com.example.expense_tracker.dto;

import com.example.expense_tracker.model.CustomField;
import java.util.List;

public class AddEventResponseDTO {
    private String eventId;
    private String eventName;
    private long eventDate;
    private List<CustomField> customFields;
    private String userId;
    private boolean isAllowNotification;
    private boolean isAllowToSeeThisEvent;
    private boolean isEventCompleted;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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