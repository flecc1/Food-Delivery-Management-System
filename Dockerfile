FROM eclipse-temurin:25-jdk-alpine AS builder
RUN adduser -D worker
USER worker
WORKDIR /app
COPY --chown=worker:worker gradlew build.gradle settings.gradle ./
COPY --chown=worker:worker ./gradle ./gradle
RUN chmod +x gradlew
COPY --chown=worker:worker ./src ./src
RUN ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:25-jre-alpine
RUN adduser -D worker
USER worker
WORKDIR /app
COPY --from=builder --chown=worker:worker /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]