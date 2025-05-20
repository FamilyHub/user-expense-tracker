package com.example.expenseTracker.repository.impl;

import com.example.expenseTracker.config.MongoProperties;
import com.example.expenseTracker.model.Transaction;
import com.example.expenseTracker.repository.TransactionRepository;
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
        Transaction transaction = mongoTemplate.findById(id, Transaction.class, mongoProperties.getTransactionCollectionName());
        return Optional.ofNullable(transaction);
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, Transaction.class, mongoProperties.getTransactionCollectionName());
    }

    @Override
    public List<Transaction> findByCategory(String category) {
        Query query = new Query(Criteria.where("category").is(category));
        return mongoTemplate.find(query, Transaction.class, mongoProperties.getTransactionCollectionName());
    }

}