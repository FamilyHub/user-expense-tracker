package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.AddEventRequestDTO;
import com.example.expense_tracker.dto.AddEventResponseDTO;
import com.example.expense_tracker.model.EventPartialUpdate;
import com.example.expense_tracker.service.AddEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class AddEventController {

    private final AddEventService addEventService;

    public AddEventController(AddEventService addEventService) {
        this.addEventService = addEventService;
    }

    @PostMapping
    public ResponseEntity<AddEventResponseDTO> createEvent(
            @RequestBody AddEventRequestDTO requestDTO,
            @RequestHeader("userId") String userId) {
        AddEventResponseDTO response = addEventService.createEvent(requestDTO, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<AddEventResponseDTO> getEventById(
            @PathVariable String eventId) {
        AddEventResponseDTO response = addEventService.getEventById(eventId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AddEventResponseDTO>> getAllEvents() {
        List<AddEventResponseDTO> events = addEventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<AddEventResponseDTO> updateEvent(
            @PathVariable String eventId,
            @RequestBody AddEventRequestDTO requestDTO) {
        AddEventResponseDTO response = addEventService.updateEvent(eventId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<AddEventResponseDTO> partialUpdateEvent(
            @PathVariable String eventId,
            @RequestBody EventPartialUpdate partialUpdate) {
        AddEventResponseDTO response = addEventService.partialUpdateEvent(eventId, partialUpdate);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String eventId) {
        addEventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/events-date-wise")
    public ResponseEntity<List<AddEventResponseDTO>> getEventsByDateWise(
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        List<AddEventResponseDTO> events = addEventService.getEventsByDateWise(startDate,endDate);
        return ResponseEntity.ok(events);
    }

    @PatchMapping("/{eventId}/status")
    public ResponseEntity<AddEventResponseDTO> updateEventStatus(
            @PathVariable String eventId,
            @RequestParam boolean status) {
        AddEventResponseDTO updatedEvent = addEventService.updateEventStatus(eventId, status);
        return ResponseEntity.ok(updatedEvent);
    }

} 