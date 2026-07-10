package com.example.jobApplication.dto;

// Pie chart data
public record StatusBreakdownDTO(
    String name,   // e.g. "Applied"
    int    value,
    String color   // CSS variable or hex for chart color
) {}
