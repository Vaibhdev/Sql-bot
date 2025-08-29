package com.example.bfhl.runner;

import com.example.bfhl.config.BfhlProps;
import com.example.bfhl.dto.FinalSubmission;
import com.example.bfhl.dto.GenerateWebhookRequest;
import com.example.bfhl.dto.GenerateWebhookResponse;
import com.example.bfhl.net.BfhlClient;
import com.example.bfhl.service.SqlLoader;
import com.example.bfhl.service.SolutionStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupRunner {
    private static final Logger log = LoggerFactory.getLogger(StartupRunner.class);

    @Bean
    ApplicationRunner run(BfhlProps props, BfhlClient client, SqlLoader sqlLoader, SolutionStore store) {
        return args -> {
            // 1) generate webhook
            String generateUrl = props.getBaseUrl() + props.getGeneratePath();
            var req = new GenerateWebhookRequest(props.getName(), props.getRegNo(), props.getEmail());
            log.info("Generating webhook at {}", generateUrl);
            GenerateWebhookResponse resp = client.generateWebhook(generateUrl, req);
            if (resp == null || resp.getAccessToken() == null) {
                throw new IllegalStateException("No accessToken in response.");
            }
            String webhookUrl = (resp.getWebhook() != null && !resp.getWebhook().isBlank())
                    ? resp.getWebhook()
                    : props.getBaseUrl() + props.getFallbackSubmitPath();

            log.info("Webhook destination resolved to: {}", webhookUrl);

            // 2) decide odd/even from last TWO digits
            int last2 = parseLastTwoDigits(props.getRegNo());
            boolean isOdd = (last2 % 2 != 0);
            log.info("regNo {} -> last two digits {} -> {}", props.getRegNo(), last2, isOdd ? "ODD (Q1)" : "EVEN (Q2)");

            // 3) load final SQL query from resources
            String finalSql = sqlLoader.loadQuery(isOdd);
            var submission = new FinalSubmission(finalSql);

            // 4) store locally (simple persistence)
            var saved = store.saveToDisk(props.getRegNo(), submission);
            log.info("Saved solution to {}", saved.getAbsolutePath());

            // 5) POST solution to webhook with Authorization header = accessToken (JWT)
            String result = client.submitSolution(webhookUrl, resp.getAccessToken(), submission);
            log.info("Submission response: {}", result);
        };
    }

    private int parseLastTwoDigits(String regNo) {
        String digits = regNo.replaceAll("\\D", "");
        if (digits.length() < 2) {
            throw new IllegalArgumentException("regNo must contain at least two digits: " + regNo);
        }
        String last2 = digits.substring(digits.length() - 2);
        return Integer.parseInt(last2);
    }
}