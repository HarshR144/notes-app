#step 1 build
FROM maven:3.9.9-eclipse-temurin-21 AS builder

#specify working dir
WORKDIR /app

#copy pom config to working dir of container
COPY pom.xml .

#s2 run
RUN mvn dependency:go-offline -B

COPY src ./src

#now package everything into a jar
RUN mvn clean package

FROM openjdk:21-jdk AS runner

WORKDIR /app
COPY --from=builder ./app/target/notes-application-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4000

ENTRYPOINT ["java","-jar","app.jar"]