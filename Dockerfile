FROM openjdk:17.0.2-jdk
WORKDIR /app
COPY target/indiaproj-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
