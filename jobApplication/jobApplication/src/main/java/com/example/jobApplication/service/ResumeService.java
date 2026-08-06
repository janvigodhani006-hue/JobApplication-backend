package com.example.jobApplication.service;

import com.example.jobApplication.Entity.Resume;
import com.example.jobApplication.Entity.User;
import com.example.jobApplication.dto.ResumeResponse;
import com.example.jobApplication.exception.BadRequestException;
import com.example.jobApplication.exception.ResourceNotFoundException;
import com.example.jobApplication.repository.ResumeRepository;
import com.example.jobApplication.repository.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final Path fileStorageLocation;

    public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.fileStorageLocation = Paths.get("uploads/resumes").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    public ResumeResponse upload(MultipartFile file, String version, String userEmail) {
        if (file.isEmpty()) {
            throw new BadRequestException("Failed to store empty file.");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.contains("..")) {
            throw new BadRequestException("Filename contains invalid path sequence " + originalFilename);
        }

        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = originalFilename.substring(dotIndex);
        }

        // Clean safe name, matching design format: e229c991_v4.pdf
        String safeName = user.getId().toString() + "_" + version + extension;

        try {
            Path targetLocation = this.fileStorageLocation.resolve(safeName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            long bytes = file.getSize();
            String fileSizeStr;
            if (bytes < 1024) {
                fileSizeStr = bytes + " B";
            } else if (bytes < 1024 * 1024) {
                fileSizeStr = String.format("%.0f KB", bytes / 1024.0);
            } else {
                fileSizeStr = String.format("%.1f MB", bytes / (1024.0 * 1024.0));
            }

            // Create relative path for response
            String relativePath = "/uploads/resumes/" + safeName;

            Resume resume = Resume.builder()
                    .user(user)
                    .name(originalFilename)
                    .version(version)
                    .filePath(relativePath)
                    .fileSize(fileSizeStr)
                    .pdfData(file.getBytes())
                    .build();

            resume = resumeRepository.save(resume);
            return toResponse(resume);

        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + originalFilename + ". Please try again!", e);
        }
    }

    public List<ResumeResponse> getAllResumes(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return resumeRepository.findByUserId(user.getId()).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Resource download(UUID id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Resume resume = resumeRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found or access denied"));

        try {
            Path filePath = this.fileStorageLocation.resolve(Paths.get(resume.getFilePath()).getFileName().toString()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File not found " + resume.getName());
            }
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException("File not found " + resume.getName());
        }
    }

    public void delete(UUID id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Resume resume = resumeRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found or access denied"));

        try {
            Path filePath = this.fileStorageLocation.resolve(Paths.get(resume.getFilePath()).getFileName().toString()).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Keep going to clean DB record even if file on disk was missing
        }

        resumeRepository.delete(resume);
    }

    private ResumeResponse toResponse(Resume resume) {
        int appCount = resumeRepository.countApplicationsByResumeId(resume.getId());
        return new ResumeResponse(
                resume.getId(),
                resume.getName(),
                resume.getVersion(),
                resume.getFilePath(),
                resume.getFileSize(),
                appCount,
                resume.getCreatedAt(),
                resume.getPdfData()
        );
    }
}
