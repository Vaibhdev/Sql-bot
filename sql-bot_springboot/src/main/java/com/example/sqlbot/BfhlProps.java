package com.example.bfhl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bfhl")
public class BfhlProps {
    private String name;
    private String regNo;
    private String email;
    private String baseUrl;
    private String generatePath;
    private String fallbackSubmitPath;

    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    public String getGeneratePath() { return generatePath; }
    public void setGeneratePath(String generatePath) { this.generatePath = generatePath; }
    public String getFallbackSubmitPath() { return fallbackSubmitPath; }
    public void setFallbackSubmitPath(String fallbackSubmitPath) { this.fallbackSubmitPath = fallbackSubmitPath; }
}