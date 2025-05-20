package com.example.expenseTracker.service;

import com.example.expenseTracker.model.ResponseTo;

public interface MonthlyAnalysisService {
    ResponseTo getMonthlyAnalysis(String userId);
} 