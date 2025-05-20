package com.example.expenseTracker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoProperties {
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private CollectionProperties collection = new CollectionProperties();

    @Data
    public static class CollectionProperties {
        private String transaction;
        private String event;

        public String getTransaction() {
            return transaction;
        }

        public void setTransaction(String transaction) {
            this.transaction = transaction;
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }
    }

    public String getTransactionCollectionName() {
        return collection.getTransaction();
    }

    public String getEventCollectionName() {
        return collection.getEvent();
    }

    public CollectionProperties getCollection() {
        return collection;
    }

    public void setCollection(CollectionProperties collection) {
        this.collection = collection;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
} 