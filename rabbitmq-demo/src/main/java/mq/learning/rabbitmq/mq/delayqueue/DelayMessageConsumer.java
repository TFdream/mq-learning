package mq.learning.rabbitmq.mq.delayqueue;

import com.rabbitmq.client.Channel;
import mq.learning.commons.util.DateUtils;
import mq.learning.commons.util.StringUtils;
import mq.learning.rabbitmq.config.DelayQueueFixedTimeConfig;
import mq.learning.rabbitmq.config.DelayQueueRandomTimeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * 测试延时队列
 *
 * @author Ricky Fung
 */
@Component
public class DelayMessageConsumer {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * 发送统一时间的延时消息
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = DelayQueueFixedTimeConfig.DEAD_LETTER_QUEUE_NAME)
    public void receiveFT(Message message, Channel channel) {
        Date now = new Date();
        String msg = StringUtils.getStringUtf8(message.getBody());
        LOG.info("【固定时间】延时队列-消费者收到消息, 当前时间={}, 消息={}", DateUtils.format(now), msg);
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 支持任意时间的延时消息
     * @param message
     * @param channel
     */
    @RabbitListener(queues = DelayQueueRandomTimeConfig.DEAD_LETTER_QUEUE_NAME)
    public void receiveRandom(Message message, Channel channel) {
        Date now = new Date();
        String msg = StringUtils.getStringUtf8(message.getBody());
        LOG.info("【任意时间】延时队列-消费者收到消息, 当前时间={}, 消息={}", DateUtils.format(now), msg);
    }
}
