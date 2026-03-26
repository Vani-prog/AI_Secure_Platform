package com.javapackage.AISecurePlatform.service;

import com.javapackage.AISecurePlatform.model.AnalysisResult;
import com.javapackage.AISecurePlatform.model.Finding;
import com.javapackage.AISecurePlatform.util.RegexDetector;
import com.javapackage.AISecurePlatform.util.RiskCalculator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogAnalysisService {

    private final PolicyService policyService;

    public LogAnalysisService(PolicyService policyService) {
        this.policyService = policyService;
    }

    // File analysis
    public AnalysisResult analyzeFile(MultipartFile file) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return analyzeText(content.toString());
    }

    // Text analysis
    public AnalysisResult analyzeText(String text) {

        streamAnalyze(text);

        boolean email = RegexDetector.containsEmail(text);
        boolean phone = RegexDetector.containsPhone(text);
        boolean apiKey = RegexDetector.containsApiKey(text);
        boolean password = RegexDetector.containsPassword(text);
        boolean error = RegexDetector.containsError(text);

        int score = RiskCalculator.calculateRiskScore(email, phone, apiKey, password, error);

        List<Finding> findings = new ArrayList<>();
        List<String> insights = new ArrayList<>();

        String[] lines = text.split("\n");
        int errorCount = 0;

        // Line-by-line detection
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if (RegexDetector.containsEmail(line)) {
                findings.add(new Finding("email", "low", i + 1));
            }

            if (RegexDetector.containsPhone(line)) {
                findings.add(new Finding("phone", "medium", i + 1));
            }

            if (RegexDetector.containsApiKey(line)) {
                findings.add(new Finding("api_key", "high", i + 1));
            }

            if (RegexDetector.containsPassword(line)) {
                findings.add(new Finding("password", "critical", i + 1));
            }

            if (RegexDetector.containsError(line)) {
                findings.add(new Finding("error", "medium", i + 1));
                errorCount++;
            }
        }

        // Insights logic
        if (email && password)
            insights.add("Possible credential leakage detected.");

        if (password && error)
            insights.add("Critical risk: Password exposed with system error.");

        if (apiKey && error)
            insights.add("API key exposed along with system error.");

        if (phone && email)
            insights.add("PII data exposure detected.");

        if (errorCount > 3)
            insights.add("Anomaly detected: Too many errors in log file.");

        if (score >= 8)
            insights.add("High risk log file. Immediate action required.");
        else if (score >= 5)
            insights.add("Moderate risk. Sensitive data present.");
        else
            insights.add("Low risk log file.");

        if (score > 5)
            insights.add("Sensitive credentials exposed in logs");

        if (error)
            insights.add("System error may reveal internal details");

        // Mask sensitive data
        text = policyService.maskSensitiveData(text);

        // Create final result
        AnalysisResult result = new AnalysisResult();

        result.setSummary("Log contains sensitive credentials and errors");
        result.setContentType("log");
        result.setRiskScore(score);

        // Risk level logic
        if (score >= 10)
            result.setRiskLevel("high");
        else if (score >= 5)
            result.setRiskLevel("medium");
        else
            result.setRiskLevel("low");

        result.setAction("masked");
        result.setFindings(findings);
        result.setInsights(insights);

        return result;
    }

    // Streaming analysis (simulates real-time log monitoring)
    public void streamAnalyze(String text) {
        String[] lines = text.split("\n");

        for (String line : lines) {
            System.out.println("Processing line: " + line);

            if (RegexDetector.containsPassword(line)) {
                System.out.println("Warning: Password detected in stream!");
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}