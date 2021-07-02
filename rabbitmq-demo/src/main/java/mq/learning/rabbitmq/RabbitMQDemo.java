package mq.learning.rabbitmq;

import mq.learning.rabbitmq.config.SpringConfiguration;
import mq.learning.rabbitmq.mq.OrderNotifySender;
import mq.learning.rabbitmq.mq.delayqueue.DelayMessageSender;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class RabbitMQDemo {

    public static void main(String[] args) throws InterruptedException {

        //ApplicationContext context = new AnnotationConfigApplicationContext("mq.tutorials.rabbitmq");
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        OrderNotifySender notifySender = context.getBean(OrderNotifySender.class);
        //发送消息
        notifySender.sendBatch();

        DelayMessageSender delayMessageSender = context.getBean(DelayMessageSender.class);
        //发送固定时间间隔的延迟消息
        delayMessageSender.sendFixedBatch();
        //发送任意时间间隔的延迟消息
        delayMessageSender.sendRandomBatch();

        TimeUnit.SECONDS.sleep(30);
    }
}
