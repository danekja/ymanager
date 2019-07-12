FROM maven:3.6-jdk-11 AS build

ARG BUILD_DIR=/app

WORKDIR $BUILD_DIR

#fetch dependencies and cache them, this will run only of pom.xml changes
COPY pom.xml .
RUN mvn install

# do the actual build
COPY src/ src/
RUN mvn clean install

FROM adoptopenjdk/openjdk11
COPY --from=build /app/target/*.jar /app/
RUN mv /app/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]