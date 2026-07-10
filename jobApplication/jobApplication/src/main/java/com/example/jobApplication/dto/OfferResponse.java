package com.example.jobApplication.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record OfferResponse(
        UUID id,
        UUID applicationId,
        String company,
        String role,
        BigDecimal base,
        String equity,
        BigDecimal bonus,
        String location,
        OffsetDateTime deadline,
        int matchPercentage,
        String status
) {}
