package com.javapackage.AISecurePlatform.service;

import org.springframework.stereotype.Service;

@Service
public class PolicyService {

    public String maskSensitiveData(String text) {

        text = text.replaceAll("password=.*", "password=******");
        text = text.replaceAll("api[_-]?key=.*", "api_key=******");

        return text;
    }
}
