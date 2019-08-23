#!/bin/bash
$JAVA_HOME/bin/java -javaagent:/opt/jmx_prometheus_javaagent-0.12.0.jar=23456:/opt/prometheus-jmx-config.yaml -jar /opt/chat1.war --server.port=12345