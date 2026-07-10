package com.example.jobApplication.service;

import com.example.jobApplication.Entity.Application;
import com.example.jobApplication.Entity.User;
import com.example.jobApplication.dto.ApplicationResponse;
import com.example.jobApplication.dto.CreateApplicationRequest;
import com.example.jobApplication.dto.UpdateApplicationRequest;
import com.example.jobApplication.exception.ResourceNotFoundException;
import com.example.jobApplication.repository.ApplicationRepository;
import com.example.jobApplication.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    public ApplicationResponse createApplication(CreateApplicationRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Application application = Application.builder()
                .company(request.company())
                .role(request.role())
                .status(request.status())
                .location(request.location())
                .salary(request.salary())
                .appliedDate(request.appliedDate())
                .source(request.source())
                .tag(request.tag())
                .logoColor(request.logoColor() != null ? request.logoColor() : "#ededed")
                .user(user)
                .build();

        application = applicationRepository.save(application);
        return toResponse(application);
    }

    public Page<ApplicationResponse> getAllApplications(Specification<Application> spec, Pageable pageable) {
        return applicationRepository.findAll(spec, pageable).map(this::toResponse);
    }

    public ApplicationResponse getApplicationById(UUID id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Application application = applicationRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        return toResponse(application);
    }

    public ApplicationResponse updateApplication(UUID id, UpdateApplicationRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Application application = applicationRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        application.setCompany(request.company());
        application.setRole(request.role());
        application.setStatus(request.status());
        application.setLocation(request.location());
        application.setSalary(request.salary());
        application.setAppliedDate(request.appliedDate());
        application.setSource(request.source());
        application.setTag(request.tag());
        application.setLogoColor(request.logoColor());

        application = applicationRepository.save(application);
        return toResponse(application);
    }

    public ApplicationResponse updateApplicationStatus(UUID id, String status, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Application application = applicationRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        application.setStatus(status);
        application = applicationRepository.save(application);
        return toResponse(application);
    }

    public void deleteApplication(UUID id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Application application = applicationRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        applicationRepository.delete(application);
    }

    private ApplicationResponse toResponse(Application application) {
        return new ApplicationResponse(
                application.getId(),
                application.getCompany(),
                application.getRole(),
                application.getStatus(),
                application.getLocation(),
                application.getSalary(),
                application.getAppliedDate(),
                application.getSource(),
                application.getTag(),
                application.getLogoColor(),
                application.getCreatedAt()
        );
    }
}