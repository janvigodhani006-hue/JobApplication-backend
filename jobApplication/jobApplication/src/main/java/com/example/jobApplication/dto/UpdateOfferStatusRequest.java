package com.example.jobApplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateOfferStatusRequest(
        @NotBlank(message = "Status is required")
        @Pattern(regexp = "accepted|rejected|negotiating", message = "Invalid status value")
        String status
) {}
