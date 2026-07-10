package com.example.jobApplication.Entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false) private String title;
    @Column(nullable = false) private String description;

    @Column(nullable = false)
    private boolean unread = true;  // Flipped to false when user reads it

    // 'interview' | 'offer' | 'reminder' | 'system'
    @Column(nullable = false) private String type;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }

    // NoArgsConstructor
    public Notification() {
    }

    // AllArgsConstructor
    public Notification(UUID id, User user, String title, String description, boolean unread, String type, OffsetDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.description = description;
        this.unread = unread;
        this.type = type;
        this.createdAt = createdAt;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUnread() {
        return unread;
    }

    public String getType() {
        return type;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return unread == that.unread &&
               Objects.equals(id, that.id) &&
               Objects.equals(user, that.user) &&
               Objects.equals(title, that.title) &&
               Objects.equals(description, that.description) &&
               Objects.equals(type, that.type) &&
               Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, title, description, unread, type, createdAt);
    }

    @Override
    public String toString() {
        return "Notification{" +
               "id=" + id +
               ", user=" + user +
               ", title='" + title + '\'' +
               ", description='" + description + '\'' +
               ", unread=" + unread +
               ", type='" + type + '\'' +
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
        private String title;
        private String description;
        private boolean unread = true;
        private String type;
        private OffsetDateTime createdAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder unread(boolean unread) {
            this.unread = unread;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Notification build() {
            return new Notification(id, user, title, description, unread, type, createdAt);
        }
    }
}
