package com.example.expenseTracker.repository.impl;

import com.example.expenseTracker.config.MongoProperties;
import com.example.expenseTracker.model.AddEvent;
import com.example.expenseTracker.repository.AddEventRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AddEventRepositoryImpl implements AddEventRepository {

    private final MongoTemplate mongoTemplate;
    private final MongoProperties mongoProperties;

    public AddEventRepositoryImpl(MongoTemplate mongoTemplate, MongoProperties mongoProperties) {
        this.mongoTemplate = mongoTemplate;
        this.mongoProperties = mongoProperties;
    }

    @Override
    public AddEvent save(AddEvent event) {
        // First save to get MongoDB's _id
        AddEvent savedEvent = mongoTemplate.save(event, mongoProperties.getEventCollectionName());
        // Get the _id and set it as eventId
        String generatedId = savedEvent.getEventId();
        savedEvent.setEventId(generatedId);
        // Save again to update the eventId
        return mongoTemplate.save(savedEvent, mongoProperties.getEventCollectionName());
    }

    @Override
    public List<AddEvent> findAll() {
        return mongoTemplate.findAll(AddEvent.class, mongoProperties.getEventCollectionName());
    }

    @Override
    public Optional<AddEvent> findById(String id) {
        AddEvent event = mongoTemplate.findById(id, AddEvent.class, mongoProperties.getEventCollectionName());
        return Optional.ofNullable(event);
    }

    @Override
    public void deleteById(String id) {
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), mongoProperties.getEventCollectionName());
    }

    @Override
    public boolean existsById(String id) {
        return mongoTemplate.exists(
            Query.query(Criteria.where("_id").is(id)),
            AddEvent.class,
            mongoProperties.getEventCollectionName()
        );
    }
} 