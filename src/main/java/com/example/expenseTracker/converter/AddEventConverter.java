package com.example.expenseTracker.converter;

import com.example.expenseTracker.dto.AddEventRequestDTO;
import com.example.expenseTracker.dto.AddEventResponseDTO;
import com.example.expenseTracker.model.AddEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AddEventConverter {

    public AddEvent toEntity(AddEventRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        AddEvent event = new AddEvent();
        event.setEventName(dto.getEventName());
        event.setEventDate(dto.getEventDate());
        event.setCustomFields(dto.getCustomFields() != null ? 
            new ArrayList<>(dto.getCustomFields()) : new ArrayList<>());
        event.setAllowNotification(dto.isAllowNotification());
        event.setAllowToSeeThisEvent(dto.isAllowToSeeThisEvent());
        event.setEventCompleted(dto.isEventCompleted());
        return event;
    }

    public AddEventResponseDTO toResponseDTO(AddEvent event) {
        if (event == null) {
            return null;
        }

        AddEventResponseDTO dto = new AddEventResponseDTO();
        dto.setEventId(event.getEventId());
        dto.setEventName(event.getEventName());
        dto.setEventDate(event.getEventDate());
        dto.setCustomFields(event.getCustomFields() != null ? 
            new ArrayList<>(event.getCustomFields()) : new ArrayList<>());
        dto.setUserId(event.getUserId());
        dto.setAllowNotification(event.isAllowNotification());
        dto.setAllowToSeeThisEvent(event.isAllowToSeeThisEvent());
        dto.setEventCompleted(event.isEventCompleted());
        return dto;
    }
} 