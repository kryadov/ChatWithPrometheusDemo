# Chat1
Chat Application with Spring Boot, Websockets, and MongoDB monitored by Prometheus.

Requirements: Java 8+, JAVA_HOME enviroment variable.

# 1. Build
gradlew build

# 2. Run
```docker-compose up -d --build```

or

Start mongodb, prometheus, exporters manually (use config directory) and execute bin\chat1.sh(.bat). 
Also add dns record "127.0.0.1 mongodb pushgateway" to /etc/hosts (Unix) 
or C:\Windows\System32\drivers\etc\hosts (Windows).

# 5. Open browser at 
Chat: http://localhost:12345

Chat JMX exporter: http://localhost:1234

Prometheus: http://localhost:9090

Pushgateway: http://localhost:9091

Grafana: http://localhost:3000

# 4. Clean data
```docker volume rm chat1_mongodata```

