package com.example.bfhl.dto;

public class FinalSubmission {
    private String finalQuery;

    public FinalSubmission() {}

    public FinalSubmission(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    public String getFinalQuery() {
        return finalQuery;
    }

    public void setFinalQuery(String finalQuery) {
        this.finalQuery = finalQuery;
    }
}

