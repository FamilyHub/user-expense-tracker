package com.example.expense_tracker.repository;

import com.example.expense_tracker.model.Transaction;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findAll();
    Optional<Transaction> findById(String id);
    void deleteById(String id);
    List<Transaction> findByCategory(String category);
} 