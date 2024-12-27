FROM openjdk:18-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install

COPY pom.xml /app/

RUN mvn dependency:go-offline

COPY . /app/

RUN mvn clean install

ENTRYPOINT ["mvn", "test"]
