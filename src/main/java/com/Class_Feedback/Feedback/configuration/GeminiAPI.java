package com.Class_Feedback.Feedback.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiAPI {

    @Value("${}")
    private String apiKey;

    @Bean
    public ChatClient chatClient(@Autowired ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }

    public String getApiKey() {
        return apiKey;
    }
}
