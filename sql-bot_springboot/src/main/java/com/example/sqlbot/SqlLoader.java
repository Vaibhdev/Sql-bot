package com.example.bfhl.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class SqlLoader {

    public String loadQuery(boolean isOdd) {
        String path = isOdd ? "sql/q1.sql" : "sql/q2.sql";
        try {
            ClassPathResource res = new ClassPathResource(path);
            return Files.readString(res.getFile().toPath(), StandardCharsets.UTF_8).trim();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load SQL from " + path + ". Put your final SQL there.", e);
        }
    }
}


