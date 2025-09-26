# Documentação de Execução - Checkpoint1 Spring Boot

Este repositório contém a aplicação **Checkpoint1** desenvolvida em **Spring Boot** com banco de dados **MySQL**. As instruções abaixo detalham como executar a aplicação, tanto utilizando a imagem publicada no **Docker Hub** quanto utilizando **docker-compose** para orquestrar os containers.

## 🚀 Execução da Aplicação

### 1. **Execução a partir da imagem publicada no Docker Hub**

Para executar a aplicação usando a **imagem Docker** publicada no Docker Hub, siga os passos abaixo:

#### Passos:

1. **Faça login no Docker Hub** com o comando:

   ```bash
   docker login
   ```

2. **Baixe a imagem publicada** no Docker Hub (substitua `seu-usuario` pelo seu nome de usuário do Docker Hub e `meu-repo` pelo nome do repositório):

   ```bash
   docker pull seu-usuario/meu-repo:1.0.0
   ```

3. **Execute a aplicação**:

   Após o download da imagem, execute a aplicação com o seguinte comando:

   ```bash
   docker run -p 8080:8080 seu-usuario/meu-repo:1.0.0
   ```

   Isso fará com que a aplicação esteja disponível na porta `8080` do seu sistema local.

4. **Acesso ao Swagger**:

   Após a execução do container, você pode acessar a interface **Swagger** da aplicação no seguinte endereço:

   ```bash
   http://localhost:8080/swagger-ui.html
   ```

   O Swagger permite visualizar e testar todos os endpoints da sua API.

---

### 2. **Execução a partir do `docker-compose`**

Se preferir usar o **docker-compose** para orquestrar os containers (API Spring Boot + Banco de Dados MySQL), siga os passos abaixo.

#### Passos:

1. **Certifique-se de que o Docker e o Docker Compose estão instalados**. Se não tiver o Docker Compose, instale-o conforme a [documentação oficial](https://docs.docker.com/compose/install/).

2. **Clone o repositório** (se ainda não tiver feito):

   ```bash
   git clone https://github.com/seu-usuario/seu-repo.git
   ```

3. **Acesse a pasta do projeto**:

   ```bash
   cd seu-repo
   ```

4. **Execute o comando `docker-compose up --build`** para construir e iniciar os containers:

   ```bash
   docker-compose up --build
   ```

   Este comando irá:

   * Construir as imagens necessárias.
   * Iniciar dois containers:

     * **db**: O banco de dados MySQL.
     * **api**: A aplicação Spring Boot.

   **Nota**: O comando `--build` é necessário para garantir que a imagem da API seja construída antes de ser executada.

5. **Acesso à aplicação**:

   A aplicação estará disponível na porta `8080` do seu sistema local. Acesse a aplicação e os endpoints da API através do **Swagger**:

   ```bash
   http://localhost:8080/swagger-ui.html
   ```

   O Swagger fornecerá uma interface para visualizar todos os endpoints da sua aplicação e realizar testes diretamente na interface.

---

## 📝 **Informações Adicionais**

* **Banco de Dados**: A aplicação está configurada para usar o banco de dados **MySQL**. O banco será automaticamente criado com o nome `appdb`.

* **Credenciais do Banco de Dados**:

  * **Usuário**: `app`
  * **Senha**: `app`
  * **Banco**: `appdb`

* **Portas**:

  * **API**: A aplicação estará acessível na porta **8080**.
  * **MySQL**: O MySQL estará acessível na porta **3306** (internamente dentro do container).

---

### 📋 **Comandos Docker úteis**

* **Para parar os containers e removê-los**:

  ```bash
  docker-compose down
  ```

* **Para limpar as imagens não utilizadas**:

  ```bash
  docker system prune -a
  ```

---

### 📅 **Versionamento**

* **Versão da API**: 1.0.0
* **Versão do Docker**: Certifique-se de que está utilizando a versão mais recente do Docker para evitar problemas.

---

### 🔧 **Problemas Comuns**

* **Problema de conexão com o banco de dados**: Certifique-se de que as variáveis de ambiente no `docker-compose.yml` estão corretas, especialmente a URL do banco de dados, o nome de usuário e a senha.
* **Portas em uso**: Se a porta `8080` já estiver sendo usada por outro processo, você pode mudar a porta da API no `docker-compose.yml` ou ao rodar o comando `docker run` com a opção `-p` para mapear outra porta.

---

Esse **README.md** agora contém todas as instruções necessárias para a execução da aplicação tanto pela imagem do Docker Hub quanto usando o **docker-compose**, além de fornecer acesso ao **Swagger** para testes e visualização dos endpoints da API.

Agora é só copiar esse conteúdo para o seu arquivo **README.md** no repositório, e **fazer o commit e push**:

```bash
git add README.md
git commit -m "Adiciona documentação de execução"
git push origin main
```
