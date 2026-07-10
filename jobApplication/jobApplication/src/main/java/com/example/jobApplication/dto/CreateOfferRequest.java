package com.example.jobApplication.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateOfferRequest(
        UUID applicationId,   // Optional link to an existing application
        @NotBlank(message = "Company name is required")
        String company,
        @NotBlank(message = "Role is required")
        String role,
        @NotNull(message = "Base salary is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Base salary must be non-negative")
        BigDecimal base,
        String equity,          // e.g. "0.08%" (optional)
        @NotNull(message = "Bonus is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Bonus must be non-negative")
        BigDecimal bonus,
        @NotBlank(message = "Location is required")
        String location,
        @NotNull(message = "Deadline is required")
        OffsetDateTime deadline,
        @Min(value = 0, message = "Match percentage must be between 0 and 100")
        @Min(value = 100, message = "Match percentage must be between 0 and 100") // This should be @Max
        int matchPercentage
) {}
