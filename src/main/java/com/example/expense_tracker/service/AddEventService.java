package com.example.expense_tracker.service;

import com.example.expense_tracker.dto.AddEventRequestDTO;
import com.example.expense_tracker.dto.AddEventResponseDTO;
import com.example.expense_tracker.dto.EventStatusResponseDTO;
import com.example.expense_tracker.exception.InvalidEventDataException;
import com.example.expense_tracker.model.BulkEventResponse;
import com.example.expense_tracker.model.EventPartialUpdate;
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