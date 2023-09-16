FROM openjdk:8-alpine

LABEL authors="TWINKLE"
MAINTAINER hwinkle.cn.web <htwinkle@foxmail.com>

COPY target/web-release/web /opt/hwinkle.cn.web/web
RUN chmod -R 755 /opt/hwinkle.cn.web/
WORKDIR /opt/hwinkle.cn.web/web/

EXPOSE 9011

ENTRYPOINT ["sh", "runServer.sh","start"]