package com.example.expense_tracker.service;

import com.example.expense_tracker.dto.TransactionDTO;
import java.util.List;

public interface TransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    TransactionDTO getTransactionById(String id);
    List<TransactionDTO> getAllTransactions();
    TransactionDTO updateTransaction(String id, TransactionDTO transactionDTO);
    void deleteTransaction(String id);
    List<TransactionDTO> getTransactionsByCategory(String category);
    
    // Financial calculation methods
    double getTotalCashIn(String startDate, String endDate);
    double getTotalCashOut(String startDate, String endDate);
    double fetchBalanceForPeriod(String startDate, String endDate);
    
    // Transaction fetching by date range
    List<TransactionDTO> getTransactionsByDateRange(String startDate, String endDate);
} 