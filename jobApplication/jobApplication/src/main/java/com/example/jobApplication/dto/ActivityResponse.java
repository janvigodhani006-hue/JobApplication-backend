package com.example.jobApplication.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

// Activity feed item (read-only)
public record ActivityResponse(
    UUID           id,
    String         type,     // "moved" | "applied" | "offer" | etc.
    String         message,  // Human-readable text
    String         detail,   // Optional extra detail
    OffsetDateTime createdAt
) {}
