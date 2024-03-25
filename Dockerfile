# Stage 1: Build the application
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY uploads /app/uploads
COPY --from=build /app/target/SmartAdministratorsSystem-0.0.1-SNAPSHOT.jar SmartAdministratorsSystem.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "SmartAdministratorsSystem.jar" ]
