package mq.learning.rabbitmq.mq;

import mq.learning.rabbitmq.constant.RabbitMqConstant;
import mq.learning.rabbitmq.entity.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Ricky Fung
 */
@Component
public class OrderNotifySender {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(OrderDTO orderDTO) {
        LOG.info("生产者发送消息开始, id={}", orderDTO.getId());
        rabbitTemplate.convertAndSend(RabbitMqConstant.ORDER_FANOUT_EXCHANGE_NAME, "", orderDTO);
    }

}
