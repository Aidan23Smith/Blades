FROM openjdk:21-jdk
COPY application/target/application-1.0-all.jar /application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/application.jar"]
