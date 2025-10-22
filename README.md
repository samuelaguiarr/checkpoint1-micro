# Global Solution API

API REST para gerenciamento de ambientes desenvolvida com Spring Boot.

## Funcionalidades

- ✅ CRUD completo de ambientes
- ✅ Documentação Swagger/OpenAPI
- ✅ Integração com MySQL
- ✅ Containerização com Docker
- ✅ Testes unitários e de integração
- ✅ CI/CD com GitHub Actions

## Tecnologias

- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- MySQL 8.4
- Docker
- Maven
- Swagger/OpenAPI

## Como executar

### Pré-requisitos

- Java 17+
- Maven 3.6+
- Docker e Docker Compose

### Executando com Docker Compose

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd cp-1
```

2. Execute o projeto:
```bash
docker-compose up -d
```

3. Acesse a aplicação:
- API: http://localhost:8080
- Swagger UI: http://localhost:8080

### Executando localmente

1. Configure o MySQL:
```sql
CREATE DATABASE api;
CREATE USER 'root'@'localhost' IDENTIFIED BY 'root_pwd';
GRANT ALL PRIVILEGES ON api.* TO 'root'@'localhost';
```

2. Execute a aplicação:
```bash
mvn spring-boot:run
```

## Testes

Execute os testes unitários:
```bash
mvn test
```

Execute os testes de integração:
```bash
mvn verify
```

## API Endpoints

### Ambientes

- `GET /api/ambientes` - Lista todos os ambientes
- `GET /api/ambientes/{id}` - Busca ambiente por ID
- `POST /api/ambientes` - Cria novo ambiente
- `PUT /api/ambientes/{id}` - Atualiza ambiente
- `DELETE /api/ambientes/{id}` - Remove ambiente

### Documentação

Acesse a documentação interativa em: http://localhost:8080

## CI/CD

O projeto possui workflows automatizados:

- **CI**: Executa testes e build em push para develop/feature/hotfix
- **CD**: Faz deploy da imagem Docker em pull requests para main
- **Release**: Gera releases automáticas em push de tags para main

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── br/com/fiap/rm_550212/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── br/com/fiap/rm_550212/
            ├── controller/
            ├── service/
            └── integration/
```

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request