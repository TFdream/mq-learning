package mq.learning.rabbitmq.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Ricky Fung
 */
@Configuration
// 相当于 context:component-scan 标签
@ComponentScan(basePackages = "mq.learning.rabbitmq")
@PropertySource("classpath:rabbitmq.properties")
// 引入其他配置类，多用于分模块开发
@Import(RabbitMQConfig.class)
@EnableRabbit   //在启动入口增加@EnableRabbit注解
public class SpringConfiguration {

}
