package com.example.expense_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventPartialUpdate {
    private String eventName;
    private long eventDate;
    private String eventTime;

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

public String getEventTime() {
    return eventTime;
}

public void setEventTime(String eventTime) {
    this.eventTime = eventTime;
}

}
