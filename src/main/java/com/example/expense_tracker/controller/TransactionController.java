package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.CategoryPercentageDTO;
import com.example.expense_tracker.dto.TransactionDTO;
import com.example.expense_tracker.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(
            @RequestHeader("Authorization") String authorization,
            @RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.createTransaction(transactionDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(
            @RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/by-category")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByCategory(
            @RequestHeader("Authorization") String authorization,
            @RequestParam String category) {
        return ResponseEntity.ok(transactionService.getTransactionsByCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id,
            @RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, transactionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/top-expensive")
    public ResponseEntity<String> topExpensedCategory(
            @RequestHeader("Authorization") String authorization,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return new ResponseEntity<>(transactionService.topExpensedCategory(startDate, endDate), HttpStatus.CREATED);
    }

    @GetMapping("/category-percentage")
    public ResponseEntity<List<CategoryPercentageDTO>> fetchCategoryPercentage(
            @RequestHeader("Authorization") String authorization,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(transactionService.fetchCategoryPercentage(startDate, endDate));
    }
} 