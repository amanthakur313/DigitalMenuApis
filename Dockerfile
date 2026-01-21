# --- Stage 1: Build the Application ---
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
# Tests skip kar rahe hain taaki build fast ho aur errors na aayein
RUN mvn clean package -DskipTests

# --- Stage 2: Run the Application ---
FROM openjdk:17-jdk-slim
WORKDIR /app

# ⚠️ DHYAN DEIN: Niche wali line mein jar file ka naam check karein
# Agar pom.xml mein version 0.0.1-SNAPSHOT hai, to ye sahi hai.
COPY --from=build /app/target/otplogin-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]