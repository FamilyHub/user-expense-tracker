package com.example.expense_tracker.repository;

import com.example.expense_tracker.model.AddEvent;
import java.util.List;
import java.util.Optional;

public interface AddEventRepository {
    AddEvent save(AddEvent event);
    List<AddEvent> findAll();
    Optional<AddEvent> findById(String id);
    void deleteById(String id);
    boolean existsById(String id);
} 