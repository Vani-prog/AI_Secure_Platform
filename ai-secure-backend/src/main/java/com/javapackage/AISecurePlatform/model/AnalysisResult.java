package com.javapackage.AISecurePlatform.model;

import java.util.List;

public class AnalysisResult {

    private String summary;
    private String contentType;
    private String action;
    private int riskScore;
    private String riskLevel;
    private List<Finding> findings;
    private List<String> insights;

    // Getter and Setter for summary
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    // Getter and Setter for contentType
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    // Getter and Setter for action
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    // Getter and Setter for riskScore
    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    // Getter and Setter for riskLevel
    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    // Getter and Setter for findings (IMPORTANT CHANGE)
    public List<Finding> getFindings() {
        return findings;
    }

    public void setFindings(List<Finding> findings) {
        this.findings = findings;
    }

    // Getter and Setter for insights
    public List<String> getInsights() {
        return insights;
    }

    public void setInsights(List<String> insights) {
        this.insights = insights;
    }
}