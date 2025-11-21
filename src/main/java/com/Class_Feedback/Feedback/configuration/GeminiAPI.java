package com.Class_Feedback.Feedback.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class GeminiAPI {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String getKey() {
        return apiKey;
    }
}
