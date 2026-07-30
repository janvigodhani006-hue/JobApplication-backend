package com.example.jobApplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS Configuration — allows the React frontend on localhost
 * to call the Spring Boot backend (port 9091).
 *
 * Allowed frontend origins:
 *   http://localhost:3000  — common React / Vite dev port
 *   http://localhost:3001
 *   http://localhost:5173  — Vite default
 *   http://localhost:5174
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:3000",
                                "http://localhost:3001",
                                "http://localhost:5173",
                                "http://localhost:5174",
                                "http://localhost:8080"
//                                "http://localhost:8081",
//                                "http://localhost:8082",
//                                "http://localhost:8083"
                        )
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
