package com.Class_Feedback.Feedback.dto;

public class SentimentStats {
    private int positive;
    private int neutral;
    private int negative;
    private int total;

    public SentimentStats() {
        this.positive = 0;
        this.neutral = 0;
        this.negative = 0;
        this.total = 0;
    }

    public SentimentStats(int positive, int neutral, int negative, int total) {
        this.positive = positive;
        this.neutral = neutral;
        this.negative = negative;
        this.total = total;
    }

    // Calcula percentuais
    public int getPositivePercent() {
        return total > 0 ? (positive * 100) / total : 0;
    }

    public int getNeutralPercent() {
        return total > 0 ? (neutral * 100) / total : 0;
    }

    public int getNegativePercent() {
        return total > 0 ? (negative * 100) / total : 0;
    }

    // Getters e Setters
    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public int getNeutral() {
        return neutral;
    }

    public void setNeutral(int neutral) {
        this.neutral = neutral;
    }

    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

