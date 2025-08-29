package com.example.bfhl.dto;

public class GenerateWebhookResponse {
    private String webhook;
    private String accessToken; // JWT as per brief

    // other fields may exist; we only bind what we need
    public String getWebhook() { return webhook; }
    public void setWebhook(String webhook) { this.webhook = webhook; }
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}