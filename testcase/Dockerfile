FROM amazoncorretto:17.0.7-alpine
RUN apk add openssl
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} testcase.jar
ENTRYPOINT ["java", "-jar", "/testcase.jar"]
