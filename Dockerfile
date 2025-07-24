# Use Eclipse Temurin Java 17 (LTS)
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY . .

# Build the project using Maven Wrapper
RUN ./mvnw clean package -DskipTests

# Run the application
CMD ["java", "-jar", "target/CarbonFootPrintTrackerBackend-0.0.1-SNAPSHOT.jar"]
