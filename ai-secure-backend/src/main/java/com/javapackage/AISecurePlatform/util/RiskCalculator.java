package com.javapackage.AISecurePlatform.util;

public class RiskCalculator {

    public static int calculateRiskScore(boolean email, boolean phone, boolean apiKey, boolean password, boolean error) {
        int score = 0;

        if (email) score += 1;
        if (phone) score += 2;
        if (apiKey) score += 4;
        if (password) score += 5;
        if (error) score += 2;

        return score;
    }

    public static String getRiskLevel(int score) {
        if (score <= 2) return "LOW";
        else if (score <= 5) return "MEDIUM";
        else return "HIGH";
    }
}

