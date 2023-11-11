FROM mysql/mysql-server:8.0.23
COPY db/toco_soap.sql /docker-entrypoint-initdb.d/

FROM maven:3.8.6-amazoncorretto-8 AS build

COPY . /app

WORKDIR /app

RUN --mount=type=cache,target=/root/.m2 mvn clean install

FROM amazoncorretto:8

COPY --from=build /app/target /app

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "toco_Soap-1.0-SNAPSHOT.jar"]