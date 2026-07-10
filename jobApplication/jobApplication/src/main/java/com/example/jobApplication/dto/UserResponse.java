package com.example.jobApplication.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse(
        UUID id,
        String fullName,
        String email
) {}
