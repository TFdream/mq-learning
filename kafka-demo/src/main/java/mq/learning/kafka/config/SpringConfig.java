package mq.learning.kafka.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Ricky Fung
 */
@Configuration
@ComponentScan(basePackages = "mq.learning.kafka")
@PropertySource("classpath:application.properties")
@Import(KafkaConfig.class)
public class SpringConfig {

}
