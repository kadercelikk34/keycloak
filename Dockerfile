# Step 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build

# Set working directory inside container
WORKDIR /app

# Copy the pom.xml and download dependencies to cache them
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Create a runtime image
FROM openjdk:17-jdk-slim

# Set working directory for the runtime image
WORKDIR /app

# Copy the packaged jar file from the build stage
COPY --from=build /app/target/keycloak-0.0.1-SNAPSHOT.jar keycloak.jar

# Expose the port the app will run on (default Spring Boot port is 8080, adjust if necessary)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/keycloak.jar"]

# Optional: CMD to provide flexibility for overriding the command
# CMD ["java", "-jar", "/app/keycloak.jar"]
