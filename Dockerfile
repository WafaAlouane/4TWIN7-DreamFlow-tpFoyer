# Utilise l'image OpenJDK comme image de base
FROM openjdk:17-jdk-alpine

# Expose le port 8089 pour l'application Spring Boot
EXPOSE 8089

# Déclare un volume temporaire pour l'application
VOLUME /tmp

# Copie le fichier JAR généré dans le container
COPY target/kaddem-0.0.1-SNAPSHOT.jar kaddem.jar

# Déclare l'entrypoint pour démarrer l'application
ENTRYPOINT ["java", "-jar", "kaddem.jar"]
