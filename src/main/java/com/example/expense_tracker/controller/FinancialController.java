package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.TransactionDTO;
import com.example.expense_tracker.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/financial")
public class FinancialController {

    private final TransactionService transactionService;

    public FinancialController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/cash-in")
    public ResponseEntity<Double> getTotalCashIn(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        double totalCashIn = transactionService.getTotalCashIn(startDate, endDate);
        return ResponseEntity.ok(totalCashIn);
    }

    @GetMapping("/cash-out")
    public ResponseEntity<Double> getTotalCashOut(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        double totalCashOut = transactionService.getTotalCashOut(startDate, endDate);
        return ResponseEntity.ok(totalCashOut);
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> fetchBalanceForPeriod(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        double balance = transactionService.fetchBalanceForPeriod(startDate, endDate);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }
} 