package com.Class_Feedback.Feedback.service;

import com.Class_Feedback.Feedback.configuration.GeminiAPI;
import com.Class_Feedback.Feedback.dto.FeedBack;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassificarFeedBack {

    @Autowired
    private ChatClient chatClient;

    public String classificarSentimento(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return "neutral";
        }

        // Primeiro, tentar análise local rápida antes de chamar a API
        String textoLower = texto.toLowerCase().trim();
        
        // Palavras-chave positivas (sem negação)
        String[] palavrasPositivas = {
            "adorei", "gostei muito", "excelente", "ótimo", "perfeito", "satisfeito", 
            "feliz", "recomendo", "maravilhoso", "incrível", "fantástico", "amazing",
            "love", "great", "good", "perfect", "wonderful", "awesome", "excellent",
            "superou", "surpreendeu", "impressionou", "encantou"
        };
        
        // Palavras-chave negativas (incluindo negações)
        String[] palavrasNegativas = {
            "não gostei", "não recomendo", "não curti", "não achei", "não foi",
            "péssimo", "ruim", "terrível", "horrível", "odeio", "detesto", "insatisfeito",
            "reclamação", "problema", "erro", "falha", "bad", "terrible", "awful",
            "hate", "worst", "disappointed", "frustrated", "angry", "decepcionado",
            "lixo", "porcaria", "desperdício", "perda de tempo", "não vale", "não serve"
        };
        
        // Palavras de negação
        String[] negacoes = {"não", "nao", "nunca", "jamais", "nem", "nada", "ninguém", "no", "not", "never"};
        
        // Verificar se há negações antes de palavras positivas
        boolean temNegacao = false;
        for (String negacao : negacoes) {
            if (textoLower.contains(negacao)) {
                temNegacao = true;
                break;
            }
        }
        
        // Se tem negação, verificar contexto mais cuidadosamente
        if (temNegacao) {
            // Verificar padrões de negação + palavra positiva (ex: "não gostei", "não recomendo")
            for (String negativa : palavrasNegativas) {
                if (textoLower.contains(negativa)) {
                    System.out.println("✓ Classificado como NEGATIVE (análise local - negação detectada: " + negativa + ")");
                    System.out.println("  Feedback: " + texto);
                    return "negative";
                }
            }
            
            // Se tem negação mas não encontrou padrão negativo conhecido, usar IA para contexto
            System.out.println("⚠️ Negação detectada, mas contexto ambíguo. Usando IA para análise contextual...");
        } else {
            // Sem negação, verificar palavras positivas normalmente
            for (String palavra : palavrasPositivas) {
                if (textoLower.contains(palavra)) {
                    System.out.println("✓ Classificado como POSITIVE (análise local - palavra: " + palavra + ")");
                    System.out.println("  Feedback: " + texto);
                    return "positive";
                }
            }
        }
        
        // Verificar palavras negativas (sempre, independente de negação)
        for (String palavra : palavrasNegativas) {
            if (textoLower.contains(palavra)) {
                System.out.println("✓ Classificado como NEGATIVE (análise local - palavra: " + palavra + ")");
                System.out.println("  Feedback: " + texto);
                return "negative";
            }
        }

        // Se não encontrou palavras-chave, usar Gemini AI
        System.out.println("\n🤖 CHAMANDO GEMINI AI para análise...");
        System.out.println("   Feedback: " + texto);
        String prompt = String.format(
            "Você é um analisador de sentimentos experiente. Analise o feedback considerando CONTEXTO DO MUNDO REAL e NEGAÇÕES.\n\n" +
            "IMPORTANTE - REGRAS CRÍTICAS:\n" +
            "- 'NÃO GOSTEI' = NEGATIVO (não positivo!)\n" +
            "- 'NÃO RECOMENDO' = NEGATIVO\n" +
            "- 'NÃO CURTI' = NEGATIVO\n" +
            "- 'NÃO ACHEI BOM' = NEGATIVO\n" +
            "- Palavras de negação (não, nunca, jamais, nem) INVERTEM o sentido\n" +
            "- Considere o contexto completo, não apenas palavras isoladas\n" +
            "- Frases com 'não' + palavra positiva = NEGATIVO\n\n" +
            "Classifique o sentimento em UMA palavra: positive, negative ou neutral.\n\n" +
            "Feedback: \"%s\"\n\n" +
            "Resposta (apenas a palavra, sem explicações):",
            texto
        );

        try {
            System.out.println("   ⏳ Aguardando resposta da API Gemini...");
            long inicio = System.currentTimeMillis();
            
            String resposta = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

            long tempo = System.currentTimeMillis() - inicio;
            
            System.out.println("   ✅ Resposta recebida da IA em " + tempo + "ms");
            System.out.println("   📝 Resposta bruta da IA: [" + resposta + "]");
            
            // Processar resposta de forma mais robusta
            String sentimento = processarRespostaGemini(resposta);
            
            System.out.println("   🎯 Classificação final pela IA: " + sentimento.toUpperCase());
            System.out.println("   ✓ Processo concluído com sucesso!\n");
            
            return sentimento;
            
        } catch (Exception e) {
            System.err.println("\n❌ ERRO ao chamar Gemini AI!");
            System.err.println("   Erro: " + e.getMessage());
            System.err.println("   Usando fallback (análise básica)...");
            e.printStackTrace();
            
            // Fallback: análise básica do texto
            String fallback = analisarTextoBasico(texto);
            System.out.println("   ⚠️  Classificação por fallback: " + fallback + "\n");
            return fallback;
        }
    }
    
    private String processarRespostaGemini(String resposta) {
        if (resposta == null || resposta.trim().isEmpty()) {
            return "neutral";
        }
        
        String limpa = resposta.trim().toLowerCase();
        
        // Remover caracteres especiais
        limpa = limpa.replaceAll("[^a-z]", " ");
        limpa = limpa.replaceAll("\\s+", " ");
        limpa = limpa.trim();
        
        // Extrair primeira palavra
        String primeira = limpa.split("\\s+")[0];
        
        // Verificar todas as possibilidades
        if (primeira.startsWith("posit") || limpa.contains("positive") || limpa.contains("positivo")) {
            return "positive";
        } else if (primeira.startsWith("negat") || limpa.contains("negative") || limpa.contains("negativo")) {
            return "negative";
        } else if (primeira.startsWith("neutr") || limpa.contains("neutral") || limpa.contains("neutro")) {
            return "neutral";
        }
        
        return "neutral";
    }
    
    private String analisarTextoBasico(String texto) {
        String textoLower = texto.toLowerCase();
        
        // Verificar negações primeiro
        String[] negacoes = {"não", "nao", "nunca", "jamais", "nem", "nada"};
        boolean temNegacao = false;
        for (String neg : negacoes) {
            if (textoLower.contains(neg)) {
                temNegacao = true;
                break;
            }
        }
        
        // Padrões negativos com negação
        String[] padroesNegativos = {
            "não gostei", "não recomendo", "não curti", "não achei", "não foi bom",
            "não vale", "não serve", "não funciona", "não atendeu"
        };
        
        for (String padrao : padroesNegativos) {
            if (textoLower.contains(padrao)) {
                return "negative";
            }
        }
        
        // Contar palavras positivas vs negativas (considerando negações)
        int positivas = 0;
        int negativas = 0;
        
        String[] pos = {"bom", "ótimo", "excelente", "gostei", "adorei", "perfeito", "satisfeito"};
        String[] neg = {"ruim", "péssimo", "terrível", "horrível", "odeio", "detesto", "insatisfeito", "lixo"};
        
        for (String p : pos) {
            if (textoLower.contains(p)) {
                // Se tem negação antes da palavra positiva, conta como negativo
                if (temNegacao && textoLower.contains("não " + p) || textoLower.contains("nao " + p)) {
                    negativas++;
                } else {
                    positivas++;
                }
            }
        }
        
        for (String n : neg) {
            if (textoLower.contains(n)) negativas++;
        }
        
        if (negativas > positivas) return "negative";
        if (positivas > negativas && !temNegacao) return "positive";
        return "neutral";
    }

    public FeedBack processarFeedback(FeedBack feedback) {
        String sentimento = classificarSentimento(feedback.getText());
        feedback.setSentiment(sentimento);
        return feedback;
    }
}
