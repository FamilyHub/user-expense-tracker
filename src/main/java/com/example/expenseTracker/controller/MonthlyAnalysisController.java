package com.example.expenseTracker.controller;

import com.example.expenseTracker.model.ResponseTo;
import com.example.expenseTracker.service.MonthlyAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analysis")
public class MonthlyAnalysisController {

    private final MonthlyAnalysisService monthlyAnalysisService;

    public MonthlyAnalysisController(MonthlyAnalysisService monthlyAnalysisService) {
        this.monthlyAnalysisService = monthlyAnalysisService;
    }

    @GetMapping("/monthly/{userId}")
    public ResponseEntity<ResponseTo> getMonthlyAnalysis(
            @PathVariable String userId,
            @RequestHeader("Authorization") String authorization) {
        ResponseTo response = monthlyAnalysisService.getMonthlyAnalysis(userId);
        if (response.getError() != null) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
} 