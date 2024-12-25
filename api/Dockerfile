# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY server/build/libs/server-1.0-SNAPSHOT.jar /app/app.jar
# Expose the port your application will run on
EXPOSE 8080

