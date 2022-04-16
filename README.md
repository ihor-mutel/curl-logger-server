docker build . -t migor/curl-logger-server:0.1
docker push migor/curl-logger-server:0.1
docker run migor/curl-logger-server:0.1
