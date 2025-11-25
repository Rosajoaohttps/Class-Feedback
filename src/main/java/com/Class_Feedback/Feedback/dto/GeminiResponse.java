package com.Class_Feedback.Feedback.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiResponse {
    private String text;
    private String sentiment;

    // Construtores
    public GeminiResponse() {
    }

    public GeminiResponse(String text, String sentiment) {
        this.text = text;
        this.sentiment = sentiment;
    }

    // Getters e Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
}
