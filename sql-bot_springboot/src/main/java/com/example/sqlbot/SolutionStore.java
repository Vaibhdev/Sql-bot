package com.example.bfhl.service;

import com.example.bfhl.dto.FinalSubmission;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Instant;

@Service
public class SolutionStore {
    private final ObjectMapper om = new ObjectMapper();

    public File saveToDisk(String regNo, FinalSubmission submission) {
        try {
            var outDir = new File("solutions");
            if (!outDir.exists()) outDir.mkdirs();
            var out = new File(outDir, regNo + "-" + Instant.now().toEpochMilli() + ".json");
            om.writerWithDefaultPrettyPrinter().writeValue(out, submission);
            return out;
        } catch (Exception e) {
            throw new IllegalStateException("Could not persist solution locally", e);
        }
    }
}
