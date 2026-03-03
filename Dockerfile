FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY M1-UBO-Spring-Boot-Testing-App/ .
RUN chmod +x gradlew && ./gradlew bootJar -x test

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
