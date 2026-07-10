package com.example.jobApplication.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // @OneToOne: exactly one offer per application (enforced by UNIQUE index)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", unique = true)
    private Application application;

    @Column(nullable = false) private String company;
    @Column(nullable = false) private String role;

    @Column(nullable = false) private BigDecimal base;
    private String equity;  // Stored as string e.g. "0.08%"

    @Column(nullable = false)
    private BigDecimal bonus = BigDecimal.ZERO;

    @Column(nullable = false) private String location;
    @Column(nullable = false) private OffsetDateTime deadline;

    @Column(name = "match_percentage", nullable = false)
    private int matchPercentage;  // 0–100

    // 'pending' | 'accepted' | 'rejected' | 'negotiating'
    @Column(nullable = false)
    private String status = "pending";

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
    public Offer() {
    }

    // AllArgsConstructor
    public Offer(UUID id, User user, Application application, String company, String role, BigDecimal base, String equity, BigDecimal bonus, String location, OffsetDateTime deadline, int matchPercentage, String status, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.application = application;
        this.company = company;
        this.role = role;
        this.base = base;
        this.equity = equity;
        this.bonus = bonus;
        this.location = location;
        this.deadline = deadline;
        this.matchPercentage = matchPercentage;
        this.status = status;
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

    public BigDecimal getBase() {
        return base;
    }

    public String getEquity() {
        return equity;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public String getLocation() {
        return location;
    }

    public OffsetDateTime getDeadline() {
        return deadline;
    }

    public int getMatchPercentage() {
        return matchPercentage;
    }

    public String getStatus() {
        return status;
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

    public void setBase(BigDecimal base) {
        this.base = base;
    }

    public void setEquity(String equity) {
        this.equity = equity;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDeadline(OffsetDateTime deadline) {
        this.deadline = deadline;
    }

    public void setMatchPercentage(int matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    public void setStatus(String status) {
        this.status = status;
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
        Offer offer = (Offer) o;
        return matchPercentage == offer.matchPercentage &&
               Objects.equals(id, offer.id) &&
               Objects.equals(user, offer.user) &&
               Objects.equals(application, offer.application) &&
               Objects.equals(company, offer.company) &&
               Objects.equals(role, offer.role) &&
               Objects.equals(base, offer.base) &&
               Objects.equals(equity, offer.equity) &&
               Objects.equals(bonus, offer.bonus) &&
               Objects.equals(location, offer.location) &&
               Objects.equals(deadline, offer.deadline) &&
               Objects.equals(status, offer.status) &&
               Objects.equals(createdAt, offer.createdAt) &&
               Objects.equals(updatedAt, offer.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, application, company, role, base, equity, bonus, location, deadline, matchPercentage, status, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Offer{" +
               "id=" + id +
               ", user=" + user +
               ", application=" + application +
               ", company='" + company + '\'' +
               ", role='" + role + '\'' +
               ", base=" + base +
               ", equity='" + equity + '\'' +
               ", bonus=" + bonus +
               ", location='" + location + '\'' +
               ", deadline=" + deadline +
               ", matchPercentage=" + matchPercentage +
               ", status='" + status + '\'' +
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
        private BigDecimal base;
        private String equity;
        private BigDecimal bonus;
        private String location;
        private OffsetDateTime deadline;
        private int matchPercentage;
        private String status;
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

        public Builder base(BigDecimal base) {
            this.base = base;
            return this;
        }

        public Builder equity(String equity) {
            this.equity = equity;
            return this;
        }

        public Builder bonus(BigDecimal bonus) {
            this.bonus = bonus;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder deadline(OffsetDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder matchPercentage(int matchPercentage) {
            this.matchPercentage = matchPercentage;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
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

        public Offer build() {
            return new Offer(id, user, application, company, role, base, equity, bonus, location, deadline, matchPercentage, status, createdAt, updatedAt);
        }
    }
}
