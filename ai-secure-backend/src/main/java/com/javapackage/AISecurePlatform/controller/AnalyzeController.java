package com.javapackage.AISecurePlatform.controller;

import com.javapackage.AISecurePlatform.model.AnalysisResult;
import com.javapackage.AISecurePlatform.service.LogAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/analyze")
//@CrossOrigin
public class AnalyzeController {

    @Autowired
    private LogAnalysisService service;

    //Rate Limiting variables
    private int requestCount = 0;
    private long startTime = System.currentTimeMillis();

    @GetMapping("/")
    public String home() {
        return "AI Secure Platform is running!";
    }

    @PostMapping("/text")
    public AnalysisResult analyzeText(@RequestBody String text) {
        return service.analyzeText(text);
    }

    @PostMapping("/file")
    public AnalysisResult analyzeFile(@RequestParam("file") MultipartFile file) {

        long currentTime = System.currentTimeMillis();

        if (currentTime - startTime < 60000) {
            requestCount++;
            if (requestCount > 5) {
                throw new RuntimeException("Too many uploads. Try again after 1 minute.");
            }
        } else {
            startTime = currentTime;
            requestCount = 1;
        }

        try {
            String fileName = file.getOriginalFilename();

            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);


            if (fileName.endsWith(".log") ||
                    fileName.endsWith(".txt") ||
                    fileName.endsWith(".csv") ||
                    fileName.endsWith(".json") ||
                    fileName.endsWith(".xml"))  {

                String content = new String(file.getBytes());

                AnalysisResult result = service.analyzeText(content);

                result.setContentType(fileType);

                return result;
            } else {
                throw new RuntimeException("Only .log, .txt, .csv, .json, .xml files are supported");
            }

        } catch (Exception e) {
            throw new RuntimeException("File processing failed");
        }
    }
}
