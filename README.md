# Sistema de Monitoramento Ambiental - Checkpoint 2

**SOA - Microservices and Web Engineering**  
**6º semestre/2025 - Prof. Antonio Carlos de Lima Júnior**

## 👥 Grupo

- **Rafael Bueno Villela** - rm550275
- **Samuel Schaeffer Aguiar** - rm550212

## 📋 Descrição

Sistema de monitoramento ambiental desenvolvido para o Checkpoint 2, implementando CI/CD com GitHub Actions e Docker Hub.

## 🚀 Tecnologias

- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- H2 Database
- Maven
- Docker
- GitHub Actions

## 🛠️ Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+
- Docker

### Executando Localmente
```bash
# Clone o repositório
git clone https://github.com/samuelaguiarr/cp-1
cd cp-1

# Execute com Maven
mvn spring-boot:run
```

### Executando com Docker
```bash
# Build da imagem
docker build -t cp-1 .

# Executar container
docker run -p 8080:8080 cp-1
```

## 🐳 Docker Hub

Imagem disponível em: https://hub.docker.com/r/samuelschaeffer/cp-1

## 📊 API Endpoints

- **GET** `/api/ambientes` - Lista todos os ambientes
- **GET** `/api/ambientes/{id}` - Busca ambiente por ID
- **POST** `/api/ambientes` - Cria novo ambiente
- **PUT** `/api/ambientes/{id}` - Atualiza ambiente
- **DELETE** `/api/ambientes/{id}` - Remove ambiente

## 🔧 CI/CD Implementation

### 1. Continuous Integration (1 ponto)
- ✅ Execução de testes unitários
- ✅ Empacotamento da aplicação Java com Maven
- ✅ Trigger: push nas branches develop, feature, hotfix

### 2. Continuous Delivery (1 ponto)
- ✅ Upload da imagem Docker no Docker Hub
- ✅ Trigger: pull request na branch main

### 3. Release Generation (1 ponto)
- ✅ Geração de documentação da versão
- ✅ Geração de Release e Tag da versão
- ✅ Trigger: push de tags na branch main

## 📚 Documentação

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

## 🧪 Testes

```bash
# Executar testes
mvn test

# Executar testes de integração
mvn verify
```

## 📄 Entrega

**Arquivo de entrega:** `ENTREGA_CHECKPOINT2.txt`

**Repositórios:**
- **GitHub**: https://github.com/samuelaguiarr/cp-1
- **Docker Hub**: https://hub.docker.com/r/samuelschaeffer/cp-1

---

**Checkpoint 2 - SOA - Microservices and Web Engineering**  
**6º semestre/2025 - Prof. Antonio Carlos de Lima Júnior**
