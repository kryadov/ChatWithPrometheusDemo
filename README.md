# Chat1
Chat Application with Spring Boot, Websockets, and MongoDB monitored by Prometheus.

# 1. Build
gradlew build

# 2. Run
```docker-compose up```
or
Start mongodb, prometheus, exporters manually (use config directory) and execute bin\chat1.sh(.bat)

# 3. Clean data
```docker volume rm chat1_mongodata```

