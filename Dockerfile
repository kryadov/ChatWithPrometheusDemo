FROM adoptopenjdk:11-jre-hotspot

USER root
COPY build/libs/*.*ar /opt/
COPY bin/* /opt/
COPY config/prometheus-jmx-config.yaml /opt/
RUN chmod +x /opt/*.sh

EXPOSE 12345 23456

ENV JAVA_HOME=/opt/java/openjdk
ENTRYPOINT /bin/bash -c /opt/chat1.sh