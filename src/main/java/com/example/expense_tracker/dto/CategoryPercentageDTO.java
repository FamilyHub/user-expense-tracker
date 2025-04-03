package com.example.expense_tracker.dto;

public class CategoryPercentageDTO {
    private String category;
    private double amount;
    private double percentage;

    // Default constructor
    public CategoryPercentageDTO() {
    }

    // All args constructor
    public CategoryPercentageDTO(String category, double amount, double percentage) {
        this.category = category;
        this.amount = amount;
        this.percentage = percentage;
    }

    // Getters
    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public double getPercentage() {
        return percentage;
    }

    // Setters
    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
} 