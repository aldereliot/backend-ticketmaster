FROM eclipse-temurin:21-jdk
ARG JAR_FILE=target/tickets-0.0.1-SNAPSHOT.jar
WORKDIR /app
COPY ${JAR_FILE} ticketmaster.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ticketmaster.jar"]