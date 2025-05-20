package com.example.expenseTracker.repository;

import com.example.expenseTracker.model.AddEvent;
import java.util.List;
import java.util.Optional;

public interface AddEventRepository {
    AddEvent save(AddEvent event);
    List<AddEvent> findAll();
    Optional<AddEvent> findById(String id);
    void deleteById(String id);
    boolean existsById(String id);
} 