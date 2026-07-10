package com.example.jobApplication.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record InterviewResponse(
        UUID id,
        UUID applicationId,
        String company,
        String role,
        String type,
        OffsetDateTime interviewDate,
        String platform,
        String prepNotes,
        boolean isCompleted
) {}
