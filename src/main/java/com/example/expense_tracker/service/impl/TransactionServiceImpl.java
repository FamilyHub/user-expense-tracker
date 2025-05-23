package com.example.expense_tracker.service.impl;

import com.example.expense_tracker.converter.TransactionConverter;
import com.example.expense_tracker.dto.CategoryPercentageDTO;
import com.example.expense_tracker.dto.TransactionDTO;
import com.example.expense_tracker.exception.InvalidTransactionDataException;
import com.example.expense_tracker.model.CustomField;
import com.example.expense_tracker.model.Transaction;
import com.example.expense_tracker.repository.TransactionRepository;
import com.example.expense_tracker.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
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

    @Override
    public String topExpensedCategory(String startDate, String endDate) {

        List<TransactionDTO> transactionDTOList = getTransactionsByDateRange(startDate, endDate);

        Map<String, Double> categoryExpenseMap = new HashMap<>();

        // Iterate through transactions and sum expenses by category
        for (TransactionDTO transaction : transactionDTOList) {
            // Check if transaction is an expenditure (amountIn = false)
            if (!transaction.isAmountIn()) {
                String category = transaction.getCategory();
                double amount = transaction.getAmount();

                // Update the expense amount for this category
                categoryExpenseMap.put(category,
                        categoryExpenseMap.getOrDefault(category, 0.0) + amount);
            }
        }

        // Find the category with the highest expense amount
        String topCategory = "";
        double maxExpense = 0.0;

        for (Map.Entry<String, Double> entry : categoryExpenseMap.entrySet()) {
            if (entry.getValue() > maxExpense) {
                maxExpense = entry.getValue();
                topCategory = entry.getKey();
            }
        }

        return topCategory;
    }
    @Override
    public List<TransactionDTO> getTransactionsByCategory(String category) {
        if (!StringUtils.hasText(category)) {
            throw new InvalidTransactionDataException("Category cannot be empty");
        }

        try {
            List<Transaction> transactions = transactionRepository.findByCategory(category);
            return transactions.stream()
                    .map(transactionConverter::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InvalidTransactionDataException("Failed to fetch transactions by category: " + e.getMessage());
        }
    }

    @Override
    public List<CategoryPercentageDTO> fetchCategoryPercentage(String startDate, String endDate) {
        try {
            // Convert string dates to long timestamps
            long startTimestamp = Long.parseLong(startDate);
            long endTimestamp = Long.parseLong(endDate);

            // Validate timestamps
            if (startTimestamp > endTimestamp) {
                throw new InvalidTransactionDataException("Start date cannot be after end date");
            }

            // Get all transactions within date range
            List<Transaction> transactions = transactionRepository.findAll().stream()
                    .filter(transaction -> {
                        // Check if transaction is within date range and is an expenditure
                        CustomField transactionDateField = getTranscationField(transaction);
                        if (transactionDateField == null) return false;
                        
                        long transactionDate = Long.parseLong(transactionDateField.getFieldValue());
                        return transactionDate >= startTimestamp && 
                               transactionDate <= endTimestamp && 
                               !transaction.isAmountIn();
                    })
                    .collect(Collectors.toList());

            // Calculate total expenditure
            double totalExpenditure = transactions.stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            // Group by category and calculate percentages
            Map<String, Double> categoryAmounts = transactions.stream()
                    .collect(Collectors.groupingBy(
                            Transaction::getCategory,
                            Collectors.summingDouble(Transaction::getAmount)
                    ));

            // Convert to CategoryPercentageDTO list
            return categoryAmounts.entrySet().stream()
                    .map(entry -> {
                        double amount = entry.getValue();
                        double percentage = (amount / totalExpenditure) * 100;
                        return new CategoryPercentageDTO(entry.getKey(), amount, percentage);
                    })
                    .collect(Collectors.toList());

        } catch (NumberFormatException e) {
            throw new InvalidTransactionDataException("Invalid date format. Please provide dates as epoch timestamps");
        } catch (Exception e) {
            throw new InvalidTransactionDataException("Failed to fetch category percentages: " + e.getMessage());
        }
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