package com.example.expense_tracker.service.impl;

import com.example.expense_tracker.converter.TransactionConverter;
import com.example.expense_tracker.dto.TransactionDTO;
import com.example.expense_tracker.model.Transaction;
import com.example.expense_tracker.repository.TransactionRepository;
import com.example.expense_tracker.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                TransactionConverter transactionConverter) {
        this.transactionRepository = transactionRepository;
        this.transactionConverter = transactionConverter;
    }

    @Override
    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionConverter.toEntity(transactionDTO);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionConverter.toDTO(savedTransaction);
    }

    @Override
    public TransactionDTO getTransactionById(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return transactionConverter.toDTO(transaction);
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(transactionConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransactionDTO updateTransaction(String id, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        Transaction updatedTransaction = transactionConverter.toEntity(transactionDTO);
        updatedTransaction.setTransactionId(id); // Preserve the original ID
        Transaction savedTransaction = transactionRepository.save(updatedTransaction);
        return transactionConverter.toDTO(savedTransaction);
    }

    @Override
    @Transactional
    public void deleteTransaction(String id) {
        transactionRepository.deleteById(id);
    }
} 