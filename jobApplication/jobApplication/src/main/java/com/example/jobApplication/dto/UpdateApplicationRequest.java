package com.example.jobApplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.OffsetDateTime;

public record UpdateApplicationRequest(
        @NotBlank(message = "Company name is required")
        String company,
        @NotBlank(message = "Role is required")
        String role,
        @NotBlank(message = "Status is required")
        @Pattern(regexp = "applied|interview|offer|rejected|archived", message = "Invalid status value")
        String status,
        @NotBlank(message = "Location is required")
        String location,
        String salary, // Optional
        @NotNull(message = "Applied date is required")
        OffsetDateTime appliedDate,
        @NotBlank(message = "Source is required")
        @Pattern(regexp = "LinkedIn|Referral|Company site", message = "Invalid source value")
        String source,
        String tag, // Optional
        String logoColor // Optional hex color, default "#ededed"
) {}
