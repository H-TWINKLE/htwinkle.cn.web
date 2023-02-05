# htwinkle.cn

用 ♥ 制作新版本

### 手动启动

1. 停止容器

```shell
docker rm -f htwinkle.cn.web
```

2. 删除目录

```shell
rm -rf /home/app/htwinkle.cn.web/
```

3. 上传文件
4. 解压文件

```shell
unzip web-release*.zip -d /home/app/htwinkle.cn.web/
```

5. 授予权限

```shell
cd /home/app/htwinkle.cn.web/web && chmod +755 runServer.sh
```

6. 启动运行容器

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