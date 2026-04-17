FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# This line is crucial: it finds the newly built jar
COPY --from=build /app/target/*.jar app.jar 
ENTRYPOINT ["java", "-jar", "app.jar"]