package com.Class_Feedback.Feedback.repository;

import com.Class_Feedback.Feedback.model.FeedBackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBackEntity, Long> {
    
    // Buscar os feedbacks mais recentes
    List<FeedBackEntity> findTop10ByOrderByCreatedAtDesc();
    
    // Contar por sentimento
    long countBySentiment(String sentiment);
    
    // Buscar todos ordenados por data (mais recentes primeiro)
    List<FeedBackEntity> findAllByOrderByCreatedAtDesc();
}

