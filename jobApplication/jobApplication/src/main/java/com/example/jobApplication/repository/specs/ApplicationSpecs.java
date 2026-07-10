package com.example.jobApplication.repository.specs;

import com.example.jobApplication.Entity.Application;
import com.example.jobApplication.Entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class ApplicationSpecs {

    public static Specification<Application> forUser(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Application> hasStatus(String status) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(status)) {
                return cb.conjunction();
            }
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Application> searchTerm(String searchTerm) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(searchTerm)) {
                return cb.conjunction();
            }
            String likePattern = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("company")), likePattern),
                    cb.like(cb.lower(root.get("role")), likePattern),
                    cb.like(cb.lower(root.get("location")), likePattern)
            );
        };
    }
}
