package mq.learning.kafka.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Ricky Fung
 */
@Component
public class KafkaMsgListener {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(groupId = "group1", topics = "kafka-foo")
    public void listen1(String in) {
        LOG.info(in);
    }

}
