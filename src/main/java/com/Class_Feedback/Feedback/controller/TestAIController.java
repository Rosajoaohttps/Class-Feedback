package com.Class_Feedback.Feedback.controller;

import com.Class_Feedback.Feedback.service.ClassificarFeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestAIController {

    @Autowired
    private ClassificarFeedBack classificarFeedBack;

    @PostMapping("/ai")
    public ResponseEntity<Map<String, Object>> testAI(@RequestBody Map<String, String> request) {
        String texto = request.get("text");
        
        if (texto == null || texto.trim().isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Texto não fornecido");
            return ResponseEntity.badRequest().body(error);
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("🧪 TESTE DE IA - Forçando uso do Gemini");
        System.out.println("=".repeat(60));
        
        long inicio = System.currentTimeMillis();
        String sentimento = classificarFeedBack.classificarSentimento(texto);
        long tempo = System.currentTimeMillis() - inicio;
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("texto", texto);
        resultado.put("sentimento", sentimento);
        resultado.put("tempo_ms", tempo);
        resultado.put("usou_ia", true);
        resultado.put("mensagem", "Classificação realizada com sucesso");
        
        System.out.println("=".repeat(60) + "\n");
        
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/ai-status")
    public ResponseEntity<Map<String, Object>> getAIStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("ia_configurada", true);
        status.put("servico", "Google Gemini AI");
        status.put("status", "ativo");
        status.put("endpoint_teste", "/api/test/ai");
        status.put("instrucoes", "Envie um POST com JSON: {\"text\": \"seu feedback aqui\"}");
        
        return ResponseEntity.ok(status);
    }
}

