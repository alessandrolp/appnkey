FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/nkey-backend-*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=test", "-jar", "app.jar"]