# Build
#FROM eclipse-temurin:21-jdk-focal AS Build
FROM openjdk:21-ea-17-jdk-slim AS build
WORKDIR /app

#Copy Maven
COPY mvnw .
COPY .mvn .mvn

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B

#Copy source code and compile
COPY src ./src
RUN ./mvnw clean package -DskipTests

#Execute
#FROM eclipse-temurin:21-jre-focal
FROM openjdk:21-ea-17-jdk-slim
WORKDIR /app

#Copy jar compiled
COPY --from=build /app/target/orderservice-1.0.0.jar app.jar

#Port
EXPOSE 8080

#Start App
ENTRYPOINT ["java", "-jar", "app.jar"]