# Etapa 1: Build
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /opt/app

# Copia apenas os arquivos de dependências primeiro (para cache de layers)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código fonte
COPY src ./src

# Compila o projeto e gera o arquivo JAR
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre

# Cria o diretório de aplicação
RUN mkdir -p /opt/app

# Copia o arquivo JAR
COPY --from=build /opt/app/target/*.jar /opt/app/app.jar

# Define o diretório de trabalho
WORKDIR /opt/app

# Expõe a porta da aplicação
EXPOSE 8080

# Configurações JVM para produção
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport"

# Comando para rodar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]