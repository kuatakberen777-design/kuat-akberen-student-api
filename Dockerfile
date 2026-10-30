# Stage 1: Build
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
# Dependencies get their own layer, so changing src does not download them again
COPY build.gradle settings.gradle ./
RUN gradle dependencies --no-daemon -q > /dev/null
COPY src ./src
RUN gradle bootJar --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
