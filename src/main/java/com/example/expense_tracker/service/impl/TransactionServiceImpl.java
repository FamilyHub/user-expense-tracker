package com.example.expense_tracker.service.impl;

import com.example.expense_tracker.converter.TransactionConverter;
import com.example.expense_tracker.dto.TransactionDTO;
import com.example.expense_tracker.model.CustomField;
import com.example.expense_tracker.model.Transaction;
import com.example.expense_tracker.repository.TransactionRepository;
import com.example.expense_tracker.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
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
        
        // Initialize customFields if null
        if (transaction.getCustomFields() == null) {
            transaction.setCustomFields(new ArrayList<>());
        }

        // Add or update lastactivity field
        CustomField lastActivityField = new CustomField();
        lastActivityField.setFieldKey("lastactivity");
        lastActivityField.setFieldValue(String.valueOf(System.currentTimeMillis()));
        lastActivityField.setFieldValueType("STRING");

        // Remove existing lastactivity field if exists
        transaction.setCustomFields(transaction.getCustomFields().stream()
                .filter(field -> !"lastactivity".equals(field.getFieldKey()))
                .collect(Collectors.toList()));

        // Add new lastactivity field
        transaction.getCustomFields().add(lastActivityField);

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

    @Override
    public double getTotalCashIn(String startDate, String endDate) {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .filter(transaction -> {
                    // Check if transaction is within date range
                    CustomField lastActivity = getTranscationField(transaction);
                    if (lastActivity == null) return false;
                    
                    String lastActivityValue = lastActivity.getFieldValue();
                    return lastActivityValue.compareTo(startDate) >= 0 && 
                           lastActivityValue.compareTo(endDate) <= 0 &&
                           transaction.isAmountIn();
                })
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    @Override
    public double getTotalCashOut(String startDate, String endDate) {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .filter(transaction -> {
                    // Check if transaction is within date range
                    CustomField lastActivity = getTranscationField(transaction);
                    if (lastActivity == null) return false;
                    
                    String lastActivityValue = lastActivity.getFieldValue();
                    return lastActivityValue.compareTo(startDate) >= 0 && 
                           lastActivityValue.compareTo(endDate) <= 0 &&
                           !transaction.isAmountIn();
                })
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    @Override
    public double fetchBalanceForPeriod(String startDate, String endDate) {
        double totalCashIn = getTotalCashIn(startDate, endDate);
        double totalCashOut = getTotalCashOut(startDate, endDate);
        return totalCashIn - totalCashOut;
    }

    @Override
    public List<TransactionDTO> getTransactionsByDateRange(String startDate, String endDate) {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .filter(transaction -> {
                    CustomField lastActivity = getTranscationField(transaction);
                    if (lastActivity == null) return false;
                    
                    String lastActivityValue = lastActivity.getFieldValue();
                    return lastActivityValue.compareTo(startDate) >= 0 && 
                           lastActivityValue.compareTo(endDate) <= 0;
                })
                .map(transactionConverter::toDTO)
                .collect(Collectors.toList());
    }

    private CustomField getLastActivityField(Transaction transaction) {
        if (transaction.getCustomFields() == null) return null;
        return transaction.getCustomFields().stream()
                .filter(field -> "lastactivity".equals(field.getFieldKey()))
                .findFirst()
                .orElse(null);
    }

    private CustomField getTranscationField(Transaction transaction) {
        if (transaction.getCustomFields() == null) return null;
        return transaction.getCustomFields().stream()
                .filter(field -> "transaction-date".equals(field.getFieldKey()))
                .findFirst()
                .orElse(null);
    }
} 