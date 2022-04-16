FROM openjdk:18

ADD /build/libs/*.jar /service.jar
ADD entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

CMD ["/bin/sh", "/entrypoint.sh"]
