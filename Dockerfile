FROM azul/zulu-openjdk-alpine:11.0.4
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java","-jar","user-service.jar"]