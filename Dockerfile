# Etapa 1: Build
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /opt/app

# Copia o conteúdo do projeto para dentro do container
COPY . .

# Compila o projeto e gera o arquivo JAR
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-alpine-3.21

# Cria o diretório de aplicação dentro do container
RUN mkdir /opt/app

# Copia o arquivo JAR gerado para a etapa de build para o diretório de aplicação
COPY --from=build /opt/app/target/*.jar /opt/app/app.jar

# Define o diretório de trabalho no container
WORKDIR /opt/app

# Comando para rodar a aplicação quando o container iniciar
CMD [ "java", "-jar", "app.jar"]