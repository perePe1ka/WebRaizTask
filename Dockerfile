FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn -B clean package -DskipTests


FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
ENV TZ=Europe/Moscow \
    JAVA_OPTS="-Xms256m -Xmx512m"
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
