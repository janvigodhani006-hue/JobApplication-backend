package com.example.jobApplication.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ResumeResponse(
        UUID id,
        String name,
        String version,
        String filePath,
        String fileSize,
        int applicationCount,  // Computed by service layer (not stored in DB)
        OffsetDateTime createdAt
) {}
