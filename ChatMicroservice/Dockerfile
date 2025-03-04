# Use the official Java image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/ChatMicroservice-0.0.1-SNAPSHOT.jar chat-microservice.jar

# Expose the service port (change as needed)
EXPOSE 8083

# Command to run the application
ENTRYPOINT ["java", "-jar", "chat-microservice.jar"]