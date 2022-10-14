FROM openjdk:17-jdk-slim
MAINTAINER rohandev.com
COPY .env .env
COPY build/libs/CryptoTracker-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]
