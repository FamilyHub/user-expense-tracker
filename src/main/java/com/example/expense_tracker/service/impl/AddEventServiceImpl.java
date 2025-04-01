package com.example.expense_tracker.service.impl;

import com.example.expense_tracker.converter.AddEventConverter;
import com.example.expense_tracker.dto.AddEventRequestDTO;
import com.example.expense_tracker.dto.AddEventResponseDTO;
import com.example.expense_tracker.exception.EventNotFoundException;
import com.example.expense_tracker.exception.InvalidEventDataException;
import com.example.expense_tracker.model.AddEvent;
import com.example.expense_tracker.model.CustomField;
import com.example.expense_tracker.model.EventPartialUpdate;
import com.example.expense_tracker.repository.AddEventRepository;
import com.example.expense_tracker.service.AddEventService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddEventServiceImpl implements AddEventService {

    private final AddEventRepository addEventRepository;
    private final AddEventConverter addEventConverter;
    private final MongoTemplate mongoTemplate;

    public AddEventServiceImpl(AddEventRepository addEventRepository,
                             AddEventConverter addEventConverter,
                             MongoTemplate mongoTemplate) {
        this.addEventRepository = addEventRepository;
        this.addEventConverter = addEventConverter;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Transactional
    public AddEventResponseDTO createEvent(AddEventRequestDTO requestDTO, String userId) {
        validateEventRequest(requestDTO);
        validateUserId(userId);

        AddEvent event = addEventConverter.toEntity(requestDTO);
        event.setUserId(userId);
        
        try {
            AddEvent savedEvent = addEventRepository.save(event);
            return addEventConverter.toResponseDTO(savedEvent);
        } catch (Exception e) {
            throw new InvalidEventDataException("Failed to create event: " + e.getMessage());
        }
    }

    @Override
    public AddEventResponseDTO getEventById(String eventId) {
        validateEventId(eventId);
        
        AddEvent event = addEventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        return addEventConverter.toResponseDTO(event);
    }

    @Override
    public List<AddEventResponseDTO> getAllEvents() {
        try {
            return addEventRepository.findAll().stream()
                    .map(addEventConverter::toResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InvalidEventDataException("Failed to fetch events: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public AddEventResponseDTO updateEvent(String eventId, AddEventRequestDTO requestDTO) {
        validateEventId(eventId);
        validateEventRequest(requestDTO);

        AddEvent existingEvent = addEventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        
        AddEvent updatedEvent = addEventConverter.toEntity(requestDTO);
        updatedEvent.setEventId(eventId);
        updatedEvent.setUserId(existingEvent.getUserId());
        
        try {
            AddEvent savedEvent = addEventRepository.save(updatedEvent);
            return addEventConverter.toResponseDTO(savedEvent);
        } catch (Exception e) {
            throw new InvalidEventDataException("Failed to update event: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteEvent(String eventId) {
        validateEventId(eventId);
        
        if (!addEventRepository.existsById(eventId)) {
            throw new EventNotFoundException(eventId);
        }
        
        try {
            addEventRepository.deleteById(eventId);
        } catch (Exception e) {
            throw new InvalidEventDataException("Failed to delete event: " + e.getMessage());
        }
    }

    @Override
    public List<AddEventResponseDTO> getEventsByDateWise(String startDate , String endDate) {
        try {
             List<AddEventResponseDTO> allEvents =  addEventRepository.findAll().stream()
                    .map(addEventConverter::toResponseDTO)
                    .collect(Collectors.toList());
             return allEvents;

        } catch (Exception e) {
            throw new InvalidEventDataException("Failed to fetch events: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public AddEventResponseDTO partialUpdateEvent(String eventId, EventPartialUpdate partialUpdate) {
        try {
            validateEventId(eventId);
            
            // First check if event exists
            AddEvent existingEvent = addEventRepository.findById(eventId)
                    .orElseThrow(() -> new EventNotFoundException(eventId));

            // Update eventName if provided
            if (StringUtils.hasText(partialUpdate.getEventName())) {
                existingEvent.setEventName(partialUpdate.getEventName());
            }

            // Update eventDate if provided
            if (partialUpdate.getEventDate() > 0) {
                existingEvent.setEventDate(partialUpdate.getEventDate());
                
                // Handle customFields
                List<CustomField> customFields = existingEvent.getCustomFields();
                if (customFields == null) {
                    customFields = new ArrayList<>();
                }

                // Check if eventTime field exists
                boolean eventTimeExists = customFields.stream()
                        .anyMatch(field -> "eventTime".equals(field.getFieldKey()));

                if (eventTimeExists) {
                    // Update existing eventTime field
                    customFields.stream()
                            .filter(field -> "eventTime".equals(field.getFieldKey()))
                            .findFirst()
                            .ifPresent(field -> field.setFieldValue(partialUpdate.getEventTime()));
                } else {
                    // Create new eventTime field
                    CustomField eventTimeField = new CustomField();
                    eventTimeField.setFieldKey("eventTime");
                    eventTimeField.setFieldValue(partialUpdate.getEventTime());
                    eventTimeField.setFieldValueType("STRING");
                    customFields.add(eventTimeField);
                }
                existingEvent.setCustomFields(customFields);
            }

            // Save the updated event
            AddEvent updatedEvent = addEventRepository.save(existingEvent);
            return addEventConverter.toResponseDTO(updatedEvent);

        } catch (EventNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidEventDataException("Failed to update event: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public AddEventResponseDTO updateEventStatus(String eventId, boolean status) {
        try {
            validateEventId(eventId);
            
            // First check if event exists
            AddEvent existingEvent = addEventRepository.findById(eventId)
                    .orElseThrow(() -> new EventNotFoundException(eventId));

            // Update the status
            existingEvent.setEventCompleted(status);

            // Save the updated event
            AddEvent updatedEvent = addEventRepository.save(existingEvent);
            return addEventConverter.toResponseDTO(updatedEvent);

        } catch (EventNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidEventDataException("Failed to update event status: " + e.getMessage());
        }
    }

    private void validateEventRequest(AddEventRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new InvalidEventDataException("Event request cannot be null");
        }
        if (!StringUtils.hasText(requestDTO.getEventName())) {
            throw new InvalidEventDataException("Event name cannot be empty");
        }
        if (requestDTO.getEventDate() <= 0) {
            throw new InvalidEventDataException("Invalid event date");
        }
    }

    private void validateEventId(String eventId) {
        if (!StringUtils.hasText(eventId)) {
            throw new InvalidEventDataException("Event ID cannot be empty");
        }
    }

    private void validateUserId(String userId) {
        if (!StringUtils.hasText(userId)) {
            throw new InvalidEventDataException("User ID cannot be empty");
        }
    }
} 