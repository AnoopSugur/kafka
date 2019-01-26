FROM openjdk:8-jdk-alpine3.7 AS builder
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN apk --no-cache add maven && mvn --version
RUN mvn package

# Stage 2 (to create a downsized "container executable", ~87MB)
FROM openjdk:8-jre-alpine3.7
WORKDIR /root/
COPY --from=builder /usr/src/myapp/target/KafkaService-0.0.1-SNAPSHOT.jar .

EXPOSE 8123
ENTRYPOINT ["java", "-jar", "./KafkaService-0.0.1-SNAPSHOT.jar"]
