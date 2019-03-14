FROM openjdk:8-jre
RUN apt-get update && apt-get install -y maven git openjdk-8-jdk
RUN rm -rf /tmp/source/*
ADD	./ /tmp/source
RUN	cd /tmp/source && mvn package -Pdocker -Dmaven.test.skip
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/tmp/source/target/prometheus-alert-webhook-1.0-SNAPSHOT.jar"]
