package mq.tutorials.rabbitmq.springboot.mq;

import mq.tutorials.rabbitmq.springboot.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Ricky Fung
 */
@RabbitListener(queues = Constant.QUEUE_NAME)
@Component
public class MsgReceiver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String msg){
        logger.info("收到消息: {}", msg);
    }

}
