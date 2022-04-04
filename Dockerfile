FROM openjdk:16.0.1
EXPOSE 80
VOLUME /tmp
ARG JAR_FILE=microservice-attraction/build/libs/microserviceAttraction.jar
COPY ${JAR_FILE} microserviceAttraction.jar
ENTRYPOINT ["java","-jar","/microserviceAttraction.jar"]