# ---- Etapa 1: Compilar la app ----
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia el pom y descarga dependencias primero (cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el codigo fuente y compila
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Etapa 2: Imagen final liviana ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]