FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY gradient/mvnw .
COPY gradient/.mvn .mvn
COPY gradient/pom.xml .
COPY gradient/src src
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
