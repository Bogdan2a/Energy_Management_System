# Use the official Java image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/MonitoringMicroservice-0.0.1-SNAPSHOT.jar monitoring-microservice.jar

# Expose the service port (change as needed)
EXPOSE 8082

# Command to run the application
ENTRYPOINT ["java", "-jar", "monitoring-microservice.jar"]