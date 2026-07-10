package com.example.jobApplication.dto;

import java.util.List;

// Top-level analytics response for the dashboard page
public record DashboardStatsResponse(
    int                         totalApps,
    int                         activeApps,       // status NOT IN ('rejected','archived')
    int                         interviewsCount,
    int                         offersCount,
    int                         rejectionsCount,
    double                      successRate,      // (offersCount / totalApps) * 100
    List<MonthlyTrendDTO>       monthlyTrends,    // Per-month application + interview counts
    List<StatusBreakdownDTO>    statusBreakdowns, // Pie chart data
    List<SourceBreakdownDTO>    sourceBreakdowns  // Bar chart data
) {}
