FROM openjdk:11

WORKDIR /app
COPY build/libs/reactfunc-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","reactfunc-0.0.1-SNAPSHOT.jar"]
