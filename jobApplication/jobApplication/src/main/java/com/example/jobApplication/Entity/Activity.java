package com.example.jobApplication.Entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 'moved' | 'applied' | 'offer' | 'resume' | 'rejected' | 'note'
    @Column(nullable = false) private String type;
    @Column(nullable = false) private String message;   // e.g. "Applied to Google"
    private String detail;  // Optional: e.g. resume filename

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    // No @PreUpdate — activities are never modified after creation
    @PrePersist protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }

    // NoArgsConstructor
    public Activity() {
    }

    // AllArgsConstructor
    public Activity(UUID id, User user, String type, String message, String detail, OffsetDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.message = message;
        this.detail = detail;
        this.createdAt = createdAt;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) &&
               Objects.equals(user, activity.user) &&
               Objects.equals(type, activity.type) &&
               Objects.equals(message, activity.message) &&
               Objects.equals(detail, activity.detail) &&
               Objects.equals(createdAt, activity.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, type, message, detail, createdAt);
    }

    @Override
    public String toString() {
        return "Activity{" +
               "id=" + id +
               ", user=" + user +
               ", type='" + type + '\'' +
               ", message='" + message + '\'' +
               ", detail='" + detail + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private User user;
        private String type;
        private String message;
        private String detail;
        private OffsetDateTime createdAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Activity build() {
            return new Activity(id, user, type, message, detail, createdAt);
        }
    }
}
