package com.example.jobApplication.dto;

// Bar chart data
public record SourceBreakdownDTO(
    String source,  // e.g. "LinkedIn"
    int    count
) {}
