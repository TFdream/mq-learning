# mq-learning
Kafka, RabbitMQ, RocketMQ learning.

## Kafka
1.拉取镜像：
```
$ docker pull zookeeper:3.6.3
$ docker pull wurstmeister/kafka:2.13-2.7.0
```
2.创建一个kafka的文件目录，后续所有操作都在该目录，然后创建docker-compose.yml文件
```
mkdir /kafka-scripts
cd /kafka-scripts
vim docker-compose.yml
```
3.docker-compose.yml文件内容如下:
```
version: '3'

services:
  zookeeper:
    image: zookeeper:3.6.3
    volumes:
      - ./zk/data:/data
    ports:
      - 2182:2181

  kafka:
    image: wurstmeister/kafka:2.13-2.7.0
    ports:
      - 9092:9092
    environment:
      KAFKA_BROKER_ID: 0
      KAFKA_ADVERTISED_HOST_NAME: 172.26.23.199
      KAFKA_ADVERTISED_PORT: 9092
      KAFKA_CREATE_TOPICS: "oms_order:2:1"   #kafka启动后初始化一个有2个partition(分区)1个副本名叫oms_order的topic
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - ./kafka/logs:/kafka/logs
    depends_on:
      - zookeeper
```
4.启动
执行命令:
```
$ docker-compose up -d
```

## RabbitMQ
拉取镜像：
```
$ docker pull rabbitmq:3.8.18-management
```

启动容器：
```
$ docker run -d --hostname my-rabbit --name rabbitmq -p 8080:15672 -p 5672:5672 rabbitmq:3.8.18-management
```

打开浏览器访问: http://localhost:8080 or http://host-ip:8080

详细内容请参考: https://hub.docker.com/_/rabbitmq

## License
[Apache License V2.0](LICENSE)