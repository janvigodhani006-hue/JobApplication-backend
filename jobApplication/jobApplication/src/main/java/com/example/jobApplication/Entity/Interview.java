package com.example.jobApplication.Entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "interviews")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Nullable: interview may exist without a linked application
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    @Column(nullable = false) private String company;
    @Column(nullable = false) private String role;
    @Column(nullable = false) private String type;  // "Technical Screen", "Behavioral", etc.

    @Column(name = "interview_date", nullable = false)
    private OffsetDateTime interviewDate;

    @Column(nullable = false) private String platform;  // "Zoom", "Google Meet", "On-site"

    @Column(name = "prep_notes", columnDefinition = "TEXT")
    private String prepNotes;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist protected void onCreate() {
        createdAt = updatedAt = OffsetDateTime.now();
    }
    @PreUpdate  protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    // NoArgsConstructor
    public Interview() {
    }

    // AllArgsConstructor
    public Interview(UUID id, User user, Application application, String company, String role, String type, OffsetDateTime interviewDate, String platform, String prepNotes, boolean isCompleted, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.application = application;
        this.company = company;
        this.role = role;
        this.type = type;
        this.interviewDate = interviewDate;
        this.platform = platform;
        this.prepNotes = prepNotes;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Application getApplication() {
        return application;
    }

    public String getCompany() {
        return company;
    }

    public String getRole() {
        return role;
    }

    public String getType() {
        return type;
    }

    public OffsetDateTime getInterviewDate() {
        return interviewDate;
    }

    public String getPlatform() {
        return platform;
    }

    public String getPrepNotes() {
        return prepNotes;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInterviewDate(OffsetDateTime interviewDate) {
        this.interviewDate = interviewDate;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setPrepNotes(String prepNotes) {
        this.prepNotes = prepNotes;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interview interview = (Interview) o;
        return isCompleted == interview.isCompleted && Objects.equals(id, interview.id) && Objects.equals(user, interview.user) && Objects.equals(application, interview.application) && Objects.equals(company, interview.company) && Objects.equals(role, interview.role) && Objects.equals(type, interview.type) && Objects.equals(interviewDate, interview.interviewDate) && Objects.equals(platform, interview.platform) && Objects.equals(prepNotes, interview.prepNotes) && Objects.equals(createdAt, interview.createdAt) && Objects.equals(updatedAt, interview.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, application, company, role, type, interviewDate, platform, prepNotes, isCompleted, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Interview{" +
               "id=" + id +
               ", user=" + user +
               ", application=" + application +
               ", company='" + company + '\'' +
               ", role='" + role + '\'' +
               ", type='" + type + '\'' +
               ", interviewDate=" + interviewDate +
               ", platform='" + platform + '\'' +
               ", prepNotes='" + prepNotes + '\'' +
               ", isCompleted=" + isCompleted +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               '}';
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private User user;
        private Application application;
        private String company;
        private String role;
        private String type;
        private OffsetDateTime interviewDate;
        private String platform;
        private String prepNotes;
        private boolean isCompleted;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder application(Application application) {
            this.application = application;
            return this;
        }

        public Builder company(String company) {
            this.company = company;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder interviewDate(OffsetDateTime interviewDate) {
            this.interviewDate = interviewDate;
            return this;
        }

        public Builder platform(String platform) {
            this.platform = platform;
            return this;
        }

        public Builder prepNotes(String prepNotes) {
            this.prepNotes = prepNotes;
            return this;
        }

        public Builder isCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
            return this;
        }

        public Builder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Interview build() {
            return new Interview(id, user, application, company, role, type, interviewDate, platform, prepNotes, isCompleted, createdAt, updatedAt);
        }
    }
}
