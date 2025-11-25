package com.Class_Feedback.Feedback.controller;

import com.Class_Feedback.Feedback.dto.FeedBack;
import com.Class_Feedback.Feedback.dto.FeedBackResponse;
import com.Class_Feedback.Feedback.dto.SentimentStats;
import com.Class_Feedback.Feedback.model.FeedBackEntity;
import com.Class_Feedback.Feedback.repository.FeedBackRepository;
import com.Class_Feedback.Feedback.service.ClassificarFeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ClassificarFeedBack classificarFeedBack;
    
    @Autowired
    private FeedBackRepository feedBackRepository;

    @GetMapping
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/api/feedback")
    @ResponseBody
    public ResponseEntity<FeedBackResponse> submitFeedback(@RequestBody FeedBack feedback) {
        if (feedback.getText() == null || feedback.getText().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Processar feedback e classificar sentimento
        feedback = classificarFeedBack.processarFeedback(feedback);
        
        // Criar entidade para salvar no banco
        FeedBackEntity entity = new FeedBackEntity();
        entity.setText(feedback.getText());
        entity.setEmail(feedback.getEmail());
        entity.setSentiment(feedback.getSentiment());
        
        // Salvar no banco de dados
        entity = feedBackRepository.save(entity);

        // Criar resposta
        FeedBackResponse response = new FeedBackResponse(
            entity.getId(),
            entity.getEmail(),
            entity.getText(),
            entity.getSentiment(),
            entity.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/feedback")
    @ResponseBody
    public ResponseEntity<List<FeedBackResponse>> getAllFeedbacks() {
        List<FeedBackEntity> entities = feedBackRepository.findTop10ByOrderByCreatedAtDesc();
        
        List<FeedBackResponse> responses = entities.stream()
            .map(entity -> new FeedBackResponse(
                entity.getId(),
                entity.getEmail(),
                entity.getText(),
                entity.getSentiment(),
                entity.getCreatedAt()
            ))
            .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/api/stats")
    @ResponseBody
    public ResponseEntity<SentimentStats> getStats() {
        long total = feedBackRepository.count();
        long positive = feedBackRepository.countBySentiment("positive");
        long neutral = feedBackRepository.countBySentiment("neutral");
        long negative = feedBackRepository.countBySentiment("negative");

        SentimentStats stats = new SentimentStats(
            (int) positive, 
            (int) neutral, 
            (int) negative, 
            (int) total
        );
        return ResponseEntity.ok(stats);
    }
}
