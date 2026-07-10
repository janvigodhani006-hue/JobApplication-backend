package com.example.jobApplication.controller;

import com.example.jobApplication.dto.ResumeResponse;
import com.example.jobApplication.service.ResumeService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumeResponse> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("version") String version,
            @AuthenticationPrincipal UserDetails userDetails) {
        ResumeResponse response = resumeService.upload(file, version, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResumeResponse>> getAllResumes(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<ResumeResponse> responses = resumeService.getAllResumes(userDetails.getUsername());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadResume(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {
        Resource fileResource = resumeService.download(id, userDetails.getUsername());
        
        String contentType = "application/octet-stream";
        try {
            String detectedType = fileResource.getURL().openConnection().getContentType();
            if (detectedType != null) {
                contentType = detectedType;
            }
        } catch (IOException e) {
            // Use default octet-stream if detection fails
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {
        resumeService.delete(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
