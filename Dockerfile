FROM openjdk:11.0.8-jdk
MAINTAINER capgem
VOLUME /tmp
ARG JAR_FILE=target/capgem.jar
COPY ${JAR_FILE} capgem.jar
EXPOSE 9854 9855
ENTRYPOINT ["java","-jar","capgem.jar"]