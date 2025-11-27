# Spring Rest Gemini AI

Integração de Spring Web com a api rest do Gemini AI.

Uma página web para conjulgar verbos em diferentes tempos verbais com o uso da Generative AI da Google, Gemini AI.

![](img.png)

## Como utilizar

Crie uma  chave de api no Google Ai Studio. https://aistudio.google.com

Adicione a sua chave no application.properties.
```
gemini.api.key=SUA-CHAVE-AQUI
```

Modelo utilizado: gemini-2.5-flash-lite.

## Integração

Integração realizada na classe ConjulgarVerboService.


## Engenharia de Prompt

Prompt utilizado.

```
Conjugue o verbo :VERBO em todos os tempos verbais simples: Presente, Pretérito Perfeito, Pretérito Imperfeito, Pretérito Mais-que-perfeito, Futuro do Presente e Futuro do Pretérito.. Adicione o sujeito. Devolve apenas o json. Por exemplo Eu corro, tu corres. [ { nome: presente, valores:[Eu corro,tu corres] } ]
```

O body completo enviado na requisição:

```
{
   "contents":[
      {
         "parts":[
            {
               "text":"Conjugue o verbo :VERBO em todos os tempos verbais simples: Presente, Pretérito Perfeito, Pretérito Imperfeito, Pretérito Mais-que-perfeito, Futuro do Presente e Futuro do Pretérito.. Adicione o sujeito. Devolve apenas o json. Por exemplo Eu corro, tu corres. [ { nome: presente, valores:[Eu corro,tu corres] } ]"
            }
         ]
      }
   ]
}
```

Resultado esperado.

```
{
  "candidates": [
    {
      "content": {
        "parts": [
          {
            "text": "```json\n[\n  {\n    \"nome\": \"Presente\",\n    \"valores\": [\n      \"Eu corro\",\n      \"Tu corres\",\n      \"Ele/Ela/Você corre\",\n      \"Nós corremos\",\n      \"Vós correis\",\n      \"Eles/Elas/Vocês correm\"\n    ]\n  },\n  {\n    \"nome\": \"Passado\",\n    \"valores\": [\n      \"Eu corri\",\n      \"Tu correste\",\n      \"Ele/Ela/Você correu\",\n      \"Nós corremos\",\n      \"Vós correstes\",\n      \"Eles/Elas/Vocês correram\"\n    ]\n  },\n  {\n    \"nome\": \"Futuro\",\n    \"valores\": [\n      \"Eu correrei\",\n      \"Tu correrás\",\n      \"Ele/Ela/Você correrá\",\n      \"Nós correremos\",\n      \"Vós correreis\",\n      \"Eles/Elas/Vocês correrão\"\n    ]\n  }\n]\n```"
          }
        ],
        "role": "model"
      },
      "finishReason": "STOP",
      "index": 0
    }
  ],
  "usageMetadata": {
    "promptTokenCount": 57,
    "candidatesTokenCount": 244,
    "totalTokenCount": 301,
    "promptTokensDetails": [
      {
        "modality": "TEXT",
        "tokenCount": 57
      }
    ]
  },
  "modelVersion": "gemini-2.5-flash-lite",
  "responseId": "xzIWaYHJLYjQz7IPt8T2gA4"
}
```

Documentação oficial: https://ai.google.dev/gemini-api/docs


```
sql - cod

-- Script SQL para criar o banco de dados e tabela de feedbacks
-- Execute este script no MySQL Workbench

-- Criar banco de dados (se não existir)
CREATE DATABASE IF NOT EXISTS feedback_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Usar o banco de dados
USE feedback_db;

-- Criar tabela de feedbacks
CREATE TABLE IF NOT EXISTS feedbacks (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
text TEXT NOT NULL,
email VARCHAR(255),
sentiment VARCHAR(20) NOT NULL,
created_at DATETIME NOT NULL,
INDEX idx_sentiment (sentiment),
INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Verificar se a tabela foi criada
SELECT 'Tabela feedbacks criada com sucesso!' AS Status;

-- Mostrar estrutura da tabela
DESCRIBE feedbacks;

Select * from feedbacks
```