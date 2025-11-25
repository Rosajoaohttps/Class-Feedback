package com.Class_Feedback.Feedback.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FeedBackResponse {
    private Long id;
    private String user;
    private String text;
    private String sentiment;
    private String time;

    public FeedBackResponse() {
    }

    public FeedBackResponse(Long id, String email, String text, String sentiment, LocalDateTime createdAt) {
        this.id = id;
        this.user = (email != null && !email.isEmpty()) ? email : "Anonymous";
        this.text = text;
        this.sentiment = sentiment;
        this.time = formatTime(createdAt);
    }

    private String formatTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "Just now";
        }
        
        LocalDateTime now = LocalDateTime.now();
        long minutes = java.time.Duration.between(dateTime, now).toMinutes();
        
        if (minutes < 1) {
            return "Just now";
        } else if (minutes < 60) {
            return minutes + "m ago";
        } else {
            long hours = minutes / 60;
            if (hours < 24) {
                return hours + "h ago";
            } else {
                return dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            }
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

