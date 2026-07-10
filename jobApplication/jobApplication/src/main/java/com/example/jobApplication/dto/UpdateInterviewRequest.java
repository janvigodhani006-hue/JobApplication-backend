package com.example.jobApplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UpdateInterviewRequest(
        UUID applicationId, // Optional link to an existing application
        @NotBlank(message = "Company name is required")
        String company,
        @NotBlank(message = "Role is required")
        String role,
        @NotBlank(message = "Interview type is required")
        @Pattern(regexp = "Technical Screen|Behavioral|Final Round", message = "Invalid interview type value")
        String type,
        @NotNull(message = "Interview date is required")
        OffsetDateTime interviewDate,
        @NotBlank(message = "Platform is required")
        @Pattern(regexp = "Zoom|Google Meet|On-site", message = "Invalid platform value")
        String platform,
        String prepNotes // Optional prep notes
) {}
