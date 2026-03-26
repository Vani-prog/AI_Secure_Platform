package com.javapackage.AISecurePlatform.service;

import com.javapackage.AISecurePlatform.model.AnalysisResult;
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

    public AnalysisResult analyzeText(String text) {

        streamAnalyze(text);

        boolean email = RegexDetector.containsEmail(text);
        boolean phone = RegexDetector.containsPhone(text);
        boolean apiKey = RegexDetector.containsApiKey(text);
        boolean password = RegexDetector.containsPassword(text);
        boolean error = RegexDetector.containsError(text);

        int score = RiskCalculator.calculateRiskScore(email, phone, apiKey, password, error);
        String level = RiskCalculator.getRiskLevel(score);

        List<String> findings = new ArrayList<>();
        List<String> insights = new ArrayList<>();

        String[] lines = text.split("\n");

        int errorCount = 0;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if (RegexDetector.containsEmail(line))
                findings.add("Email detected at line " + (i + 1));

            if (RegexDetector.containsPhone(line))
                findings.add("Phone detected at line " + (i + 1));

            if (RegexDetector.containsApiKey(line))
                findings.add("API key detected at line " + (i + 1));

            if (RegexDetector.containsPassword(line))
                findings.add("Password detected at line " + (i + 1));

            if (RegexDetector.containsError(line)) {
                findings.add("Error detected at line " + (i + 1));
                errorCount++;
            }
        }

        // Correlation detection
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


        text = policyService.maskSensitiveData(text);

        AnalysisResult result = new AnalysisResult();
        result.setSummary("File security analysis completed");
        result.setContentType("log");
        result.setAction("masked");
        result.setRiskScore(score);
        result.setRiskLevel(level);
        result.setFindings(findings);
        result.setInsights(insights);

        return result;
    }


    public void streamAnalyze(String text) {
        String[] lines = text.split("\n");

        for (String line : lines) {
            System.out.println("Processing line: " + line);

            if (RegexDetector.containsPassword(line)) {
                System.out.println("Warning: Password detected in stream!");
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}