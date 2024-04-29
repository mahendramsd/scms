FROM openjdk:17-jdk-alpine
MAINTAINER minden.ai
COPY target/scms-api-0.0.1-SNAPSHOT.jar /opt/scms-api.jar
RUN chmod +x /opt/scms-api.jar
EXPOSE 8080
WORKDIR /opt/
USER root
ENTRYPOINT ["java", "-jar", "/opt/scms-api.jar"]
