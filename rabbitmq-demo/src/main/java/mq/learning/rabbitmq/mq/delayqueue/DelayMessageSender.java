package mq.learning.rabbitmq.mq.delayqueue;

import mq.learning.commons.util.DateUtils;
import mq.learning.commons.util.JsonUtils;
import mq.learning.commons.util.RandomUtils;
import mq.learning.rabbitmq.config.DelayQueueFixedTimeConfig;
import mq.learning.rabbitmq.config.DelayQueueRandomTimeConfig;
import mq.learning.rabbitmq.entity.CloseOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author Ricky Fung
 */
@Component
public class DelayMessageSender {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 配合 rabbitmq_delayed_message_exchange插件使用
     * @param orderDTO
     * @param delayMs
     */
    public void sendDelayMsg(CloseOrderDTO orderDTO, int delayMs) {
        rabbitTemplate.convertAndSend(DelayQueueRandomTimeConfig.DELAY_EXCHANGE_NAME, DelayQueueRandomTimeConfig.DELAY_QUEUE_ROUTING_KEY, orderDTO, msg ->{
            msg.getMessageProperties().setDelay(delayMs);
            return msg;
        });
    }

    //========方式二
    /**
     * 发送任意时间间隔
     */
    public void sendRandomBatch() {
        LocalDateTime now = LocalDateTime.now();
        Date jdkNow = DateUtils.convertToDateViaInstant(now);
        for (int i=0; i<10; i++) {
            CloseOrderDTO orderDTO = new CloseOrderDTO();
            orderDTO.setOrderNo(String.format("ES20210702%s", RandomUtils.genRandomCode(6)));

            int delayMs = RandomUtils.nextInt(10000, 30000);
            orderDTO.setCloseTime(DateUtils.convertToDateViaInstant(now.plus(delayMs, ChronoUnit.MILLIS)));
            orderDTO.setSendTime(jdkNow);
            orderDTO.setDelayMs(delayMs);

            sendRandomDelay(orderDTO, delayMs);
        }
    }

    public void sendRandomDelay(CloseOrderDTO orderDTO, int delayMs) {
        LOG.info("【任意时间间隔】延时队列-生产者发送消息开始, 消息={}", JsonUtils.toJson(orderDTO));

        rabbitTemplate.convertAndSend(DelayQueueRandomTimeConfig.DELAY_EXCHANGE_NAME, DelayQueueRandomTimeConfig.DELAY_QUEUE_ROUTING_KEY, orderDTO, msg ->{
            msg.getMessageProperties().setExpiration(String.valueOf(delayMs));
            return msg;
        });
    }

    //=========
    public void sendFixedBatch() {
        LocalDateTime now = LocalDateTime.now();
        Date jdkNow = DateUtils.convertToDateViaInstant(now);
        for (int i=0; i<10; i++) {
            CloseOrderDTO orderDTO = new CloseOrderDTO();
            orderDTO.setOrderNo(String.format("ES20210702%s", RandomUtils.genRandomCode(5)));
            orderDTO.setSendTime(jdkNow);

            sendDelayMsg(orderDTO);
        }
    }

    public void sendDelayMsg(CloseOrderDTO orderDTO) {
        LOG.info("【固定时间间隔】延时队列-生产者发送消息开始, 消息={}", JsonUtils.toJson(orderDTO));
        rabbitTemplate.convertAndSend(DelayQueueFixedTimeConfig.DELAY_EXCHANGE_NAME, DelayQueueFixedTimeConfig.DELAY_QUEUE_ROUTING_KEY, orderDTO);
    }

}
