package com.example.jobApplication.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String token,
        UserResponse user
) {}
