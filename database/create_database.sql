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

