FROM openjdk:18-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install -y maven wget unzip \
    xvfb libxi6 libgconf-2-4 \
    && apt-get install -y gnupg2 curl \
    && curl -sS https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

RUN wget -O allure-2.13.9.tgz https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.13.9/allure-commandline-2.13.9.tgz \
    && tar -zxvf allure-2.13.9.tgz -C /opt/ \
    && ln -s /opt/allure-2.13.9/bin/allure /usr/bin/allure \
    && rm allure-2.13.9.tgz

ENV DISPLAY=:99

COPY pom.xml /app/
RUN mvn dependency:go-offline

COPY . /app/

RUN mvn clean install

CMD ["sh", "-c", "Xvfb :99 -ac & sleep 5 && mvn test && allure generate /app/target/allure-results -o /app/target/site/allure-maven-plugin && ls -la /app/target/site/allure-maven-plugin"]
