FROM openjdk:18-jdk-slim

WORKDIR /app

COPY pom.xml /app/

RUN mvn dependency:go-offline

COPY . /app/

RUN mvn clean install

ENTRYPOINT ["mvn", "test"]
