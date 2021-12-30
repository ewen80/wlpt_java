FROM openjdk:8-jdk-alpine
RUN mkdir uploads templates
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]