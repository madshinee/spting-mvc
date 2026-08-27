FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

RUN groupadd -r spring && useradd -r -g spring spring
RUN chown -R spring:spring /app
USER spring:spring

COPY --from=builder /app/target/diti4_spring_mvc.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
