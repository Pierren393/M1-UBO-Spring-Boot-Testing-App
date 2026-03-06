FROM gradle:8.12-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle :authentication-api:bootJar -x test

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/authentication-api/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
