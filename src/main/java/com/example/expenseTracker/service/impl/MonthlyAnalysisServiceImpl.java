package com.example.expenseTracker.service.impl;

import com.example.expenseTracker.model.ResponseTo;
import com.example.expenseTracker.service.MonthlyAnalysisService;
import com.example.expenseTracker.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthlyAnalysisServiceImpl implements MonthlyAnalysisService {

    private final TransactionService transactionService;

    public MonthlyAnalysisServiceImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public ResponseTo getMonthlyAnalysis(String userId) {
        try {
            // For now, using a mock registration date (3 months ago)
            LocalDateTime registrationDate = LocalDateTime.now().minusMonths(3);
            LocalDateTime currentDate = LocalDateTime.now();

            List<Double> totalCashIn = new ArrayList<>();
            List<Double> totalCashOut = new ArrayList<>();

            // Get the first day of registration month
            YearMonth registrationMonth = YearMonth.from(registrationDate);
            YearMonth currentMonth = YearMonth.from(currentDate);

            // Iterate through each month from registration to current
            YearMonth month = registrationMonth;
            while (!month.isAfter(currentMonth)) {
                // Calculate start and end dates for the month
                LocalDateTime monthStart = month.atDay(1).atStartOfDay();
                LocalDateTime monthEnd = month.atEndOfMonth().atTime(23, 59, 59);

                // Convert to epoch milliseconds
                String startDate = String.valueOf(monthStart
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli());
                
                String endDate = String.valueOf(monthEnd
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli());

                // Get cash flow for the month
                double cashIn = transactionService.getTotalCashIn(startDate, endDate);
                double cashOut = transactionService.getTotalCashOut(startDate, endDate);

                totalCashIn.add(cashIn);
                totalCashOut.add(cashOut);

                month = month.plusMonths(1);
            }

            return new ResponseTo(totalCashIn, totalCashOut, null, userId);

        } catch (Exception e) {
            return new ResponseTo(null, null, e.getMessage(), userId);
        }
    }
} 