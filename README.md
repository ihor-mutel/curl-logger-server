```
./gradlew build
docker build . -t migor/curl-logger-server:0.1
docker push migor/curl-logger-server:0.1
docker run -p 8080:8080 migor/curl-logger-server:0.1
```
