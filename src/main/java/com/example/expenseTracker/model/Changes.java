package com.example.expenseTracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Changes {
    private String description;
    private String oldValue;
    private String currentValue;

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }
}