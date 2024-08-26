FROM openjdk:17-jdk-slim


WORKDIR /app


COPY target/challenge-0.0.1-SNAPSHOT.jar challenge.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "challenge.jar"]