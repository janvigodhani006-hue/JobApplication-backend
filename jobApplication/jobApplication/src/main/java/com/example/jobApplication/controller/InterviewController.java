package com.example.jobApplication.controller;

import com.example.jobApplication.dto.CreateInterviewRequest;
import com.example.jobApplication.dto.InterviewResponse;
import com.example.jobApplication.dto.UpdateInterviewRequest;
import com.example.jobApplication.service.InterviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    public ResponseEntity<InterviewResponse> createInterview(
            @Valid @RequestBody CreateInterviewRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        InterviewResponse response = interviewService.createInterview(request, userDetails.getUsername());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InterviewResponse>> getAllInterviews(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<InterviewResponse> interviews = interviewService.getAllInterviews(userDetails.getUsername());
        return ResponseEntity.ok(interviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewResponse> getInterviewById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(interviewService.getInterviewById(id, userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterviewResponse> updateInterview(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateInterviewRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(interviewService.updateInterview(id, request, userDetails.getUsername()));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<InterviewResponse> markInterviewCompleted(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(interviewService.markInterviewCompleted(id, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {
        interviewService.deleteInterview(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
