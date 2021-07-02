package mq.learning.rabbitmq;

import mq.learning.commons.util.DecimalUtils;
import mq.learning.commons.util.RandomUtils;
import mq.learning.rabbitmq.config.SpringConfiguration;
import mq.learning.rabbitmq.entity.OrderDTO;
import mq.learning.rabbitmq.mq.OrderNotifySender;
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

        for (int i=0; i<10; i++) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(Long.valueOf(i+1));
            orderDTO.setOrderNo(String.format("ES20210702%s", RandomUtils.genRandomCode(5)));
            orderDTO.setTotalAmount(DecimalUtils.valueOf(5000+i));
            notifySender.sendMsg(orderDTO);
        }

        TimeUnit.SECONDS.sleep(30);
    }
}
