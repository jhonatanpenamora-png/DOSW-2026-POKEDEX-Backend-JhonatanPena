# =============================================
# STAGE 1: Build
# =============================================
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copiar solo los archivos de configuración primero (cacheo de layers)
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Descargar dependencias (sin código fuente aún)
RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -q

# Copiar el código fuente y compilar
COPY src src
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean package -DskipTests -q

# =============================================
# STAGE 2: Run
# =============================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Crear usuario no-root
RUN addgroup -S pokedex && adduser -S pokedex -G pokedex

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

USER pokedex

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
