# --- Stage 1: Build the Application ---
# Hum Maven ke sath Eclipse Temurin use kar rahe hain (Latest & Stable)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- Stage 2: Run the Application ---
# Runtime ke liye bhi Eclipse Temurin use karenge
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# JAR file ka naam copy kar rahe hain
COPY --from=build /app/target/otplogin-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]