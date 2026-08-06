package com.example.jobApplication.Entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false) private String name;     // e.g. "Software_General_v4.pdf"
    @Column(nullable = false) private String version;  // e.g. "v1", "v2"

    @Column(name = "file_path", nullable = false)
    private String filePath;   // Storage path or S3 URL

    @Column(name = "file_size", nullable = false)
    private String fileSize;   // Display string: "184 KB"

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "PDF File" , columnDefinition = "BYTEA")
    private byte[] pdfData;

    @PrePersist protected void onCreate() {
        createdAt = updatedAt = OffsetDateTime.now();
    }
    @PreUpdate  protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    // NoArgsConstructor
    public Resume() {
    }

    // AllArgsConstructor
    public Resume(UUID id, User user, String name, String version, String filePath, String fileSize, OffsetDateTime createdAt, OffsetDateTime updatedAt,byte[] pdfData) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.version = version;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.pdfData = pdfData;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public byte[] getPdfData() {
        return pdfData;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setPdfData(byte[] pdfData) {
        this.pdfData = pdfData;
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(id, resume.id) &&
               Objects.equals(user, resume.user) &&
               Objects.equals(name, resume.name) &&
               Objects.equals(version, resume.version) &&
               Objects.equals(filePath, resume.filePath) &&
               Objects.equals(fileSize, resume.fileSize) &&
               Objects.equals(createdAt, resume.createdAt) &&
               Objects.equals(updatedAt, resume.updatedAt) &&
                Objects.equals(pdfData, resume.pdfData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, name, version, filePath, fileSize, createdAt, updatedAt,pdfData);
    }

    @Override
    public String toString() {
        return "Resume{" +
               "id=" + id +
               ", user=" + user +
               ", name='" + name + '\'' +
               ", version='" + version + '\'' +
               ", filePath='" + filePath + '\'' +
               ", fileSize='" + fileSize + '\'' +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
                ", pdfData=" + pdfData +

               '}';
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private User user;
        private String name;
        private String version;
        private String filePath;
        private String fileSize;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;
        private byte[] pdfData;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder fileSize(String fileSize) {
            this.fileSize = fileSize;
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
        public Builder pdfData(byte[] pdfData) {
            this.pdfData = pdfData;
            return this;
        }

        public Resume build() {
            return new Resume(id, user, name, version, filePath, fileSize, createdAt, updatedAt,pdfData);
        }
    }
}
