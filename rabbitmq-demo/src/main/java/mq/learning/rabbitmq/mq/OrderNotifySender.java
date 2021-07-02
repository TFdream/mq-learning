package mq.learning.rabbitmq.mq;

import mq.learning.commons.util.DecimalUtils;
import mq.learning.commons.util.RandomUtils;
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

    public void sendBatch() {
        for (int i=0; i<10; i++) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(Long.valueOf(i+1));
            orderDTO.setOrderNo(String.format("ES20210702%s", RandomUtils.genRandomCode(5)));
            orderDTO.setTotalAmount(DecimalUtils.valueOf(5000+i));
            sendMsg(orderDTO);
        }
    }

    public void sendMsg(OrderDTO orderDTO) {
        LOG.info("生产者发送消息开始, id={}", orderDTO.getId());
        rabbitTemplate.convertAndSend(RabbitMqConstant.ORDER_FANOUT_EXCHANGE_NAME, "", orderDTO);
    }

}
