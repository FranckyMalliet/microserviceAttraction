FROM openjdk:16.0.1
VOLUME /tmp
ARG JAR_FILE=target/microserviceAttraction.jar
COPY ${JAR_FILE} microserviceAttraction.jar
ENTRYPOINT ["java","-jar","/microserviceAttraction.jar"]