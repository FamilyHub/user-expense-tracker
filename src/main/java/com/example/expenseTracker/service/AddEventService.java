package com.example.expenseTracker.service;

import com.example.expenseTracker.dto.AddEventRequestDTO;
import com.example.expenseTracker.dto.AddEventResponseDTO;
import com.example.expenseTracker.dto.EventStatusResponseDTO;
import com.example.expenseTracker.exception.InvalidEventDataException;
import com.example.expenseTracker.model.BulkEventResponse;
import com.example.expenseTracker.model.EventPartialUpdate;
import java.util.List;

public interface AddEventService {
    AddEventResponseDTO createEvent(AddEventRequestDTO requestDTO, String userId);
    AddEventResponseDTO getEventById(String eventId);
    List<AddEventResponseDTO> getAllEvents();
    AddEventResponseDTO updateEvent(String eventId, AddEventRequestDTO requestDTO);
    AddEventResponseDTO partialUpdateEvent(String eventId, EventPartialUpdate partialUpdate);
    void deleteEvent(String eventId);
    List<AddEventResponseDTO> getEventsByDateWise(String startDate , String endDate);
    AddEventResponseDTO updateEventStatus(String eventId, boolean status);
    BulkEventResponse bulkDeleteEvents(List<String> eventIds);
    List<AddEventResponseDTO> fetchAllPendingEvents(String startDate,String endDate);
    EventStatusResponseDTO getEventStatus(String startDate, String endDate) throws InvalidEventDataException;
} 