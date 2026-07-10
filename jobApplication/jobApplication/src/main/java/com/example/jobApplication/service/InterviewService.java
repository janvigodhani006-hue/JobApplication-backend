package com.example.jobApplication.service;

import com.example.jobApplication.Entity.Application;
import com.example.jobApplication.Entity.Interview;
import com.example.jobApplication.Entity.User;
import com.example.jobApplication.dto.CreateInterviewRequest;
import com.example.jobApplication.dto.InterviewResponse;
import com.example.jobApplication.dto.UpdateInterviewRequest;
import com.example.jobApplication.exception.ResourceNotFoundException;
import com.example.jobApplication.repository.ApplicationRepository;
import com.example.jobApplication.repository.InterviewRepository;
import com.example.jobApplication.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public InterviewService(InterviewRepository interviewRepository, UserRepository userRepository, ApplicationRepository applicationRepository) {
        this.interviewRepository = interviewRepository;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
    }

    public InterviewResponse createInterview(CreateInterviewRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Application application = null;
        if (request.applicationId() != null) {
            application = applicationRepository.findByIdAndUserId(request.applicationId(), user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Application not found for this user"));
        }

        Interview interview = Interview.builder()
                .company(request.company())
                .role(request.role())
                .type(request.type())
                .interviewDate(request.interviewDate())
                .platform(request.platform())
                .prepNotes(request.prepNotes())
                .isCompleted(false) // Newly created interviews are not completed
                .user(user)
                .application(application)
                .build();

        interview = interviewRepository.save(interview);
        return toResponse(interview);
    }

    public List<InterviewResponse> getAllInterviews(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return interviewRepository.findByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public InterviewResponse getInterviewById(UUID id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Interview interview = interviewRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found for this user"));
        return toResponse(interview);
    }

    public InterviewResponse updateInterview(UUID id, UpdateInterviewRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Interview interview = interviewRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found for this user"));

        Application application = null;
        if (request.applicationId() != null) {
            application = applicationRepository.findByIdAndUserId(request.applicationId(), user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Application not found for this user"));
        }

        interview.setCompany(request.company());
        interview.setRole(request.role());
        interview.setType(request.type());
        interview.setInterviewDate(request.interviewDate());
        interview.setPlatform(request.platform());
        interview.setPrepNotes(request.prepNotes());
        interview.setApplication(application);

        interview = interviewRepository.save(interview);
        return toResponse(interview);
    }

    public InterviewResponse markInterviewCompleted(UUID id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Interview interview = interviewRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found for this user"));

        interview.setCompleted(true);
        interview = interviewRepository.save(interview);
        return toResponse(interview);
    }

    public void deleteInterview(UUID id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Interview interview = interviewRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found for this user"));
        interviewRepository.delete(interview);
    }

    private InterviewResponse toResponse(Interview interview) {
        return new InterviewResponse(
                interview.getId(),
                interview.getApplication() != null ? interview.getApplication().getId() : null,
                interview.getCompany(),
                interview.getRole(),
                interview.getType(),
                interview.getInterviewDate(),
                interview.getPlatform(),
                interview.getPrepNotes(),
                interview.isCompleted()
        );
    }
}
