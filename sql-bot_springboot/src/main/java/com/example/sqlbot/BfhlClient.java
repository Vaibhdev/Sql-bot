package com.example.bfhl.net;

import com.example.bfhl.dto.GenerateWebhookRequest;
import com.example.bfhl.dto.GenerateWebhookResponse;
import com.example.bfhl.dto.FinalSubmission;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class BfhlClient {

    private final WebClient webClient;

    public BfhlClient(WebClient.Builder builder) {
        this.webClient = builder
                .codecs(c -> c.defaultCodecs().maxInMemorySize(512 * 1024))
                .build();
    }

    public GenerateWebhookResponse generateWebhook(String url, GenerateWebhookRequest body) {
        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(GenerateWebhookResponse.class)
                .retryWhen(Retry.backoff(2, Duration.ofMillis(400)))
                .block(Duration.ofSeconds(10));
    }

    public String submitSolution(String submitUrl, String jwt, FinalSubmission submission) {
        return webClient.post()
                .uri(submitUrl)
                .header(HttpHeaders.AUTHORIZATION, jwt) // NOTE: brief shows token directly (no "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(submission)
                .retrieve()
                .bodyToMono(String.class)
                .block(Duration.ofSeconds(10));
    }
}
