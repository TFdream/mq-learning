# mq-learning
Kafka, RabbitMQ, RocketMQ learning.


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