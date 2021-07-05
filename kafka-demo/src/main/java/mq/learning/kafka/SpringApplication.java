package mq.learning.kafka;

import mq.learning.kafka.config.SpringConfig;
import mq.learning.kafka.mq.KafkaMsgProducer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Ricky Fung
 */
public class SpringApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        KafkaMsgProducer producer = context.getBean(KafkaMsgProducer.class);

        producer.sendBatch();

        System.out.println("启动成功....");
    }
}
