FROM openjdk:8-jdk-alpine
MAINTAINER demo.com
COPY target/employee-managment-0.0.1.jar employee-managment.jar
ENTRYPOINT ["java","-jar","/employee-managment.jar"]