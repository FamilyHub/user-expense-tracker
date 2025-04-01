package com.example.expense_tracker.repository.impl;

import com.example.expense_tracker.config.MongoProperties;
import com.example.expense_tracker.model.Transaction;
import com.example.expense_tracker.repository.TransactionRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final MongoTemplate mongoTemplate;
    private final MongoProperties mongoProperties;

    public TransactionRepositoryImpl(MongoTemplate mongoTemplate, MongoProperties mongoProperties) {
        this.mongoTemplate = mongoTemplate;
        this.mongoProperties = mongoProperties;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return mongoTemplate.save(transaction, mongoProperties.getTransactionCollectionName());
    }

    @Override
    public List<Transaction> findAll() {
        return mongoTemplate.findAll(Transaction.class, mongoProperties.getTransactionCollectionName());
    }

    @Override
    public Optional<Transaction> findById(String id) {
        Transaction transaction = mongoTemplate.findById(id, Transaction.class, mongoProperties.getEventCollectionName());
        return Optional.ofNullable(transaction);
    }

    @Override
    public void deleteById(String id) {
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), mongoProperties.getTransactionCollectionName());
    }
}