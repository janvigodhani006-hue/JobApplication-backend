package com.example.jobApplication.controller;

import com.example.jobApplication.Entity.Application;
import com.example.jobApplication.Entity.User;
import com.example.jobApplication.dto.ApplicationResponse;
import com.example.jobApplication.dto.CreateApplicationRequest;
import com.example.jobApplication.dto.UpdateApplicationRequest;
import com.example.jobApplication.repository.UserRepository;
import com.example.jobApplication.repository.specs.ApplicationSpecs;
import com.example.jobApplication.service.ApplicationService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final UserRepository userRepository;

    public ApplicationController(ApplicationService applicationService, UserRepository userRepository) {
        this.applicationService = applicationService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(
            @Valid @RequestBody CreateApplicationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        ApplicationResponse response = applicationService.createApplication(request, userDetails.getUsername());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public Page<ApplicationResponse> getAllApplications(
            @AuthenticationPrincipal UserDetails userDetails,
//            @RequestParam(required = false) String search,
//            @RequestParam(required = false) String status,
            @ParameterObject Pageable pageable) {
        
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        Specification<Application> spec = ApplicationSpecs.forUser(user.getId());

//        if (StringUtils.hasText(search)) {
//            spec = spec.and(ApplicationSpecs.searchTerm(search));
//        }
//
//        if (StringUtils.hasText(status)) {
//            spec = spec.and(ApplicationSpecs.hasStatus(status));
//        }
        
        return applicationService.getAllApplications(spec, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getApplicationById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(applicationService.getApplicationById(id, userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> updateApplication(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateApplicationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(applicationService.updateApplication(id, request, userDetails.getUsername()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponse> updateApplicationStatus(
            @PathVariable UUID id,
            @RequestBody Map<String, String> statusMap,
            @AuthenticationPrincipal UserDetails userDetails) {
        String newStatus = statusMap.get("status");
//        if (!StringUtils.hasText(newStatus)) {
//            return ResponseEntity.badRequest().build();
//        }
        return ResponseEntity.ok(applicationService.updateApplicationStatus(id, newStatus, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {
        applicationService.deleteApplication(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}