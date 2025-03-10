package com.example.expense_tracker.service;

import com.example.expense_tracker.dto.TransactionDTO;
import java.util.List;

public interface TransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    TransactionDTO getTransactionById(String id);
    List<TransactionDTO> getAllTransactions();
    TransactionDTO updateTransaction(String id, TransactionDTO transactionDTO);
    void deleteTransaction(String id);
} 