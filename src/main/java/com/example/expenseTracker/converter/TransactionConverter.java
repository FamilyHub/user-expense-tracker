package com.example.expenseTracker.converter;

import com.example.expenseTracker.dto.TransactionDTO;
import com.example.expenseTracker.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {
    
    public Transaction toEntity(TransactionDTO dto) {
        if (dto == null) {
            return null;
        }
        Transaction entity = new Transaction();
        entity.setTransactionId(dto.getTransactionId());
        entity.setCategory(dto.getCategory());
        entity.setCustomFields(dto.getCustomFields());
        entity.setReceiverName(dto.getReceiverName());
        entity.setSenderName(dto.getSenderName());
        entity.setReason(dto.getReason());
        entity.setAmount(dto.getAmount());
        entity.setAmountIn(dto.isAmountIn());
        entity.setOrgId(dto.getOrgId());
        entity.setUpdates(dto.getUpdates());
        return entity;
    }

    public TransactionDTO toDTO(Transaction entity) {
        if (entity == null) {
            return null;
        }
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(entity.getTransactionId());
        dto.setCategory(entity.getCategory());
        dto.setCustomFields(entity.getCustomFields());
        dto.setReceiverName(entity.getReceiverName());
        dto.setSenderName(entity.getSenderName());
        dto.setReason(entity.getReason());
        dto.setAmount(entity.getAmount());
        dto.setAmountIn(entity.isAmountIn());
        dto.setOrgId(entity.getOrgId());
        dto.setUpdates(entity.getUpdates());
        return dto;
    }
} 