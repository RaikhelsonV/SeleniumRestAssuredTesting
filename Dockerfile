FROM openjdk:18-jdk-slim

WORKDIR /app

# Install necessary packages
RUN apt-get update && apt-get install -y maven wget unzip \
    xvfb libxi6 libgconf-2-4 \
    && apt-get install -y gnupg2 curl \
    && curl -sS https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# Install Allure CLI
RUN wget -O allure-2.13.9.tgz https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.13.9/allure-commandline-2.13.9.tgz \
    && tar -zxvf allure-2.13.9.tgz -C /opt/ \
    && ln -s /opt/allure-2.13.9/bin/allure /usr/bin/allure

# Set display port to avoid crash
ENV DISPLAY=:99

COPY pom.xml /app/

RUN mvn dependency:go-offline

COPY . /app/

RUN mvn clean install

# Run Xvfb, Maven tests, and generate Allure report
CMD ["sh", "-c", "Xvfb :99 -ac & sleep 5 && mvn test && mkdir -p /app/target/allure-report && allure generate /app/target/allure-results -o /app/target/allure-report && ls -la /app/target/allure-report"]
