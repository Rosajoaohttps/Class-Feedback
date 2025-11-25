# Configuração do Banco de Dados MySQL

## Passo a Passo

### 1. Instalar MySQL (se ainda não tiver)
- Baixe e instale o MySQL Server: https://dev.mysql.com/downloads/mysql/
- Instale o MySQL Workbench: https://dev.mysql.com/downloads/workbench/

### 2. Criar o Banco de Dados

**Opção A: Usando MySQL Workbench**
1. Abra o MySQL Workbench
2. Conecte-se ao servidor MySQL (localhost)
3. Abra o arquivo `create_database.sql`
4. Execute o script (Ctrl+Shift+Enter ou botão Execute)

**Opção B: Usando linha de comando**
```sql
mysql -u root -p < create_database.sql
```

### 3. Configurar Credenciais

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI
```

**IMPORTANTE:** Altere `root` pela sua senha do MySQL!

### 4. Verificar Conexão

Após configurar, execute o projeto. O Spring Boot irá:
- Conectar automaticamente ao banco
- Criar/atualizar a tabela `feedbacks` automaticamente (devido ao `spring.jpa.hibernate.ddl-auto=update`)

### 5. Verificar Dados

No MySQL Workbench, execute:
```sql
USE feedback_db;
SELECT * FROM feedbacks ORDER BY created_at DESC;
```

## Estrutura da Tabela

```sql
CREATE TABLE feedbacks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    text TEXT NOT NULL,
    email VARCHAR(255),
    sentiment VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL
);
```

## Troubleshooting

**Erro de conexão:**
- Verifique se o MySQL está rodando
- Verifique usuário e senha no `application.properties`
- Verifique se a porta 3306 está acessível

**Erro de permissão:**
- Certifique-se de que o usuário tem permissão para criar banco de dados
- Ou crie o banco manualmente antes de executar o projeto

