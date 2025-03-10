package com.example.expense_tracker.config;

import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoCollectionConfig {

    private final MongoProperties mongoProperties;
    private final MongoClient mongoClient;

    public MongoCollectionConfig(MongoProperties mongoProperties, MongoClient mongoClient) {
        this.mongoProperties = mongoProperties;
        this.mongoClient = mongoClient;
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient, mongoProperties.getDatabase());
    }
} 