package mq.learning.kafka.mq;

import mq.learning.commons.util.JsonUtils;
import mq.learning.commons.util.RandomUtils;
import mq.learning.kafka.entity.UserEventDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Ricky Fung
 */
@Component
public class KafkaMsgProducer {

    @Value("${topic.name:}")
    private String topic;

    @Resource
    private KafkaTemplate<String, String> template;

    public void sendBatch() {
        for (int i=0; i<100; i++) {
            UserEventDTO eventDTO = new UserEventDTO();
            eventDTO.setId(Long.valueOf(i+RandomUtils.nextInt(1000, 5000000)));
            eventDTO.setUserId(Long.valueOf(i+200));
            eventDTO.setType(i%3);
            eventDTO.setBody("哈哈哈哈");
            send(String.format("key_%s", i), JsonUtils.toJson(eventDTO));
        }
    }

    public void send(String key, String data) {
        this.template.send(topic, key, data);
    }

    public void send(String topic, String key, String data) {
        this.template.send(topic, key, data);
    }
}
