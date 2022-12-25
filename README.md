# htwinkle.cn

用 ♥ 制作新版本

## 容器启动指令

```shell
docker run -p 9011:9011 --name htwinkle.cn.web \
--network host \
-v /home/app/htwinkle.cn.web/:/home \
-v /home/backUpFile/:/home/backUpFile \
-w /home/web \
-itd rencz/jdk1.8 \
/bin/bash ./runServer.sh start
```

## 停止指令

```shell
docker rm -f htwinkle.cn.web
```

## 查看所有的状态

```shell
docker ps -a
```

## 进入容器

```shell
docker exec -it htwinkle.cn.web /bin/bash
```