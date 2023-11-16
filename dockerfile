FROM mysql/mysql-server:8.0.23
COPY db/tocosoap.sql /docker-entrypoint-initdb.d/

FROM maven:3.8-openjdk-11-slim

WORKDIR /app

COPY src ./src
COPY pom.xml .
COPY .env .
COPY target ./target

RUN mvn clean package

CMD ["mvn", "exec:java"]