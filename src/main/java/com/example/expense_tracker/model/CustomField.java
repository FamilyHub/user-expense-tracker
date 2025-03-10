package com.example.expense_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomField {
    private String fieldKey;
    private String fieldValue;
    private String fieldValueType;


    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldValueType() {
        return fieldValueType;
    }

    public void setFieldValueType(String fieldValueType) {
        this.fieldValueType = fieldValueType;
    }
}