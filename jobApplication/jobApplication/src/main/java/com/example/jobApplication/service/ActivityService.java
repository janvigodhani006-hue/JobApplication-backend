package com.example.jobApplication.service;

import com.example.jobApplication.Entity.Activity;
import com.example.jobApplication.Entity.User;
import com.example.jobApplication.dto.ActivityResponse;
import com.example.jobApplication.exception.ResourceNotFoundException;
import com.example.jobApplication.repository.ActivityRepository;
import com.example.jobApplication.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityService(ActivityRepository activityRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<ActivityResponse> getUserActivities(String userEmail, Pageable pageable) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return activityRepository.findByUserIdOrderByCreatedAtDesc(user.getId(), pageable)
                .map(this::toResponse);
    }

    @Transactional
    public Activity logActivity(User user, String type, String message, String detail) {
        Activity activity = Activity.builder()
                .user(user)
                .type(type)
                .message(message)
                .detail(detail)
                .build();
        return activityRepository.save(activity);
    }

    private ActivityResponse toResponse(Activity activity) {
        return new ActivityResponse(
                activity.getId(),
                activity.getType(),
                activity.getMessage(),
                activity.getDetail(),
                activity.getCreatedAt()
        );
    }
}
