package mq.learning.rabbitmq.mq;

import mq.learning.commons.util.StringUtils;
import mq.learning.rabbitmq.constant.RabbitMqConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Ricky Fung
 */
@Component
public class OrderNotifyReceiver {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = RabbitMqConstant.ORDER_QUEUE_NAME)
    public void process(Message message) {
        String body = StringUtils.getStringUtf8(message.getBody());
        LOG.info("消费者收到消息, body={}", body);

    }

}
