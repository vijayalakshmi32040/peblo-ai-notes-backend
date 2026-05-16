package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class AIService {

    public String generateSummary(String content) {

        if(content.length() > 100) {
            return "AI Summary: "
                    + content.substring(0, 100)
                    + "...";
        }

        return "AI Summary: " + content;
    }
}