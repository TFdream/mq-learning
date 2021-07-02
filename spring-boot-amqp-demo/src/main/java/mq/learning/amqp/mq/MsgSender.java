package mq.learning.amqp.mq;

import mq.learning.amqp.util.Constant;
import mq.learning.amqp.model.EchoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Ricky Fung
 */
@Component
public class MsgSender {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AtomicLong counter = new AtomicLong(1);

    @Resource(name = "rabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    public Long send(String msg) {
        EchoDTO echo = new EchoDTO();
        echo.setId(counter.getAndIncrement());
        echo.setContent(msg);
        echo.setTime(new Date());
        rabbitTemplate.convertAndSend(Constant.ROUTING_KEY, echo);
        logger.info("发送消息: {} 成功", msg);
        return echo.getId();
    }
}
