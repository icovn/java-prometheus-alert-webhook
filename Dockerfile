FROM openjdk:8-jre
RUN rm -rf /tmp/*
ADD	./target/prometheus-alert-webhook.jar /tmp/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "-jar","/tmp/prometheus-alert-webhook.jar"]
