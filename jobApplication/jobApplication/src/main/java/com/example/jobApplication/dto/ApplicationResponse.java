package com.example.jobApplication.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ApplicationResponse(
        UUID id,
        String company,
        String role,
        String status,
        String location,
        String salary,
        OffsetDateTime appliedDate,
        String source,
        String tag,
        String logoColor,
        OffsetDateTime createdAt
) {}
