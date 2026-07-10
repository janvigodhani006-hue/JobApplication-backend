package com.example.jobApplication.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

// Notification item
public record NotificationResponse(
    UUID           id,
    String         title,
    String         description,
    boolean        unread,
    String         type,     // "interview" | "offer" | "reminder" | "system"
    OffsetDateTime createdAt
) {}
