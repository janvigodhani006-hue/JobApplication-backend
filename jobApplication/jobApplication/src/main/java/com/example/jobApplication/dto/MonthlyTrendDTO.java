package com.example.jobApplication.dto;

// Per-month application + interview counts
public record MonthlyTrendDTO(
    String month,        // e.g. "Jun"
    int    applications,
    int    interviews
) {}
