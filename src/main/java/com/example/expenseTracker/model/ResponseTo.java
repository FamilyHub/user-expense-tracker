package com.example.expenseTracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ResponseTo {
    private List<Double> totalCashIn;
    private List<Double> totalCashOut;
    private String error;
    private String userId;

    public ResponseTo(List<Double> totalCashIn, List<Double> totalCashOut, String error, String userId) {
        this.totalCashIn = totalCashIn;
        this.totalCashOut = totalCashOut;
        this.error = error;
        this.userId = userId;
    }

    public ResponseTo() {
    }

    public List<Double> getTotalCashIn() {
        return totalCashIn;
    }

    public void setTotalCashIn(List<Double> totalCashIn) {
        this.totalCashIn = totalCashIn;
    }

    public List<Double> getTotalCashOut() {
        return totalCashOut;
    }

    public void setTotalCashOut(List<Double> totalCashOut) {
        this.totalCashOut = totalCashOut;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}