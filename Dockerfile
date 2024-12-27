FROM openjdk:18-jdk-slim

WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the pom.xml to download dependencies
COPY pom.xml /app/

# Download dependencies
RUN mvn dependency:go-offline

# Copy the project files
COPY . /app/

# Build the project
RUN mvn clean install

# Set the entry point to run the tests
ENTRYPOINT ["mvn", "test"]
