FROM openjdk:8-jre
RUN apt-get update && apt-get install -y maven git openjdk-8-jdk
RUN rm -rf /tmp/*
ADD	./target/prometheus-alert-webhook.jar /tmp/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "-jar","/tmp/prometheus-alert-webhook.jar"]
