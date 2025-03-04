# Use the official Java image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled classes and resources into the container
COPY target/smart-meter-simulator-1.0-SNAPSHOT.jar simulator.jar

# Copy the resources folder (config.json and sensor.csv) to the container
COPY src/main/resources/ /app/resources/

# Set the environment variable to define the resources path
ENV CLASSPATH="/app/resources"

# Command to run the application
ENTRYPOINT ["java", "-cp", "/app/resources:simulator.jar", "Simulator"]
