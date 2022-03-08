FROM adoptopenjdk/openjdk11:alpine
VOLUME /tmp
EXPOSE 8081
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/service-player-0.0.1-SNAPSHOT.jar /app/serviceplayer.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod", "-jar", "/app/serviceplayer.jar"]

