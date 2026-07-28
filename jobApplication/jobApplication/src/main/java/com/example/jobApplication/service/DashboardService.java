package com.example.jobApplication.service;

import com.example.jobApplication.Entity.Application;
import com.example.jobApplication.Entity.Interview;
import com.example.jobApplication.Entity.User;
import com.example.jobApplication.dto.DashboardStatsResponse;
import com.example.jobApplication.dto.MonthlyTrendDTO;
import com.example.jobApplication.dto.SourceBreakdownDTO;
import com.example.jobApplication.dto.StatusBreakdownDTO;
import com.example.jobApplication.exception.ResourceNotFoundException;
import com.example.jobApplication.repository.ApplicationRepository;
import com.example.jobApplication.repository.InterviewRepository;
import com.example.jobApplication.repository.OfferRepository;
import com.example.jobApplication.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.TextStyle;
import java.util.*;

@Service
public class DashboardService {

    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    public DashboardService(
            ApplicationRepository applicationRepository,
            InterviewRepository interviewRepository,
            OfferRepository offerRepository,
            UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.interviewRepository = interviewRepository;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public DashboardStatsResponse getDashboardStats(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UUID userId = user.getId();

        int totalApps = (int) applicationRepository.countByUserId(userId);
        int activeApps = (int) applicationRepository.countByUserIdAndStatusNotIn(userId, List.of("rejected", "archived"));
        int interviewsCount = (int) interviewRepository.countByUserId(userId);
        int offersCount = (int) offerRepository.countByUserId(userId);
        int rejectionsCount = (int) applicationRepository.countByUserIdAndStatus(userId, "rejected");

        // successRate = offers / total * 100 (rounded to 1 decimal)
        double successRate = totalApps > 0
                ? Math.round((offersCount * 1000.0) / totalApps) / 10.0
                : 0.0;

        // Monthly trends
        List<Application> applications = applicationRepository.findByUserId(userId);
        List<Interview> interviews = interviewRepository.findByUserId(userId);

        Map<String, int[]> monthlyMap = new LinkedHashMap<>();
        for (Application app : applications) {
            if (app.getAppliedDate() != null) {
                String month = app.getAppliedDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
                monthlyMap.putIfAbsent(month, new int[]{0, 0});
                monthlyMap.get(month)[0]++;
            }
        }
        for (Interview interview : interviews) {
            if (interview.getInterviewDate() != null) {
                String month = interview.getInterviewDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
                monthlyMap.putIfAbsent(month, new int[]{0, 0});
                monthlyMap.get(month)[1]++;
            }
        }

        List<MonthlyTrendDTO> monthlyTrends = new ArrayList<>();
        monthlyMap.forEach((month, counts) ->
                monthlyTrends.add(new MonthlyTrendDTO(month, counts[0], counts[1]))
        );

        // Status breakdowns
        List<ApplicationRepository.StatusCountProjection> statusProjections = applicationRepository.countByStatusForUser(userId);
        List<StatusBreakdownDTO> statusBreakdowns = new ArrayList<>();
        Map<String, String> colorMap = Map.of(
                "applied", "var(--color-chart-2)",
                "interview", "var(--color-chart-3)",
                "offer", "var(--color-chart-1)",
                "rejected", "var(--color-chart-4)",
                "archived", "var(--color-chart-5)"
        );

        for (ApplicationRepository.StatusCountProjection p : statusProjections) {
            String rawStatus = p.getStatus() != null ? p.getStatus() : "unknown";
            String formattedName = capitalize(rawStatus);
            String color = colorMap.getOrDefault(rawStatus.toLowerCase(), "var(--color-chart-2)");
            statusBreakdowns.add(new StatusBreakdownDTO(formattedName, (int) p.getCount(), color));
        }

        // Source breakdowns
        List<ApplicationRepository.SourceCountProjection> sourceProjections = applicationRepository.countBySourceForUser(userId);
        List<SourceBreakdownDTO> sourceBreakdowns = new ArrayList<>();
        for (ApplicationRepository.SourceCountProjection p : sourceProjections) {
            sourceBreakdowns.add(new SourceBreakdownDTO(p.getSource() != null ? p.getSource() : "Other", (int) p.getCount()));
        }

        return new DashboardStatsResponse(
                totalApps,
                activeApps,
                interviewsCount,
                offersCount,
                rejectionsCount,
                successRate,
                monthlyTrends,
                statusBreakdowns,
                sourceBreakdowns
        );
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
