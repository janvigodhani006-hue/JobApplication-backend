package com.example.jobApplication.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class UserDto {

    private UUID id;
    private String fullName;
    private String email;
    private String passwordHash;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public UserDto() {
    }

    // All Args Constructor
    public UserDto(UUID id, String fullName, String email,
                   String passwordHash,
                   OffsetDateTime createdAt,
                   OffsetDateTime updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Builder

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private UUID id;
        private String fullName;
        private String email;
        private String passwordHash;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
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

        public UserDto build() {
            return new UserDto(
                    id,
                    fullName,
                    email,
                    passwordHash,
                    createdAt,
                    updatedAt
            );
        }
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        return Objects.equals(id, userDto.id) &&
                Objects.equals(fullName, userDto.fullName) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(passwordHash, userDto.passwordHash) &&
                Objects.equals(createdAt, userDto.createdAt) &&
                Objects.equals(updatedAt, userDto.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                fullName,
                email,
                passwordHash,
                createdAt,
                updatedAt
        );
    }
}
