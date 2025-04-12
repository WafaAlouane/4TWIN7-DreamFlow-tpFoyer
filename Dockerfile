FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/tp-foyer-1.0.0.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]