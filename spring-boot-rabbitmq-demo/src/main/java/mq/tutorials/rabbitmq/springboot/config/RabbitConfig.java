package mq.tutorials.rabbitmq.springboot.config;

import mq.tutorials.rabbitmq.springboot.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ricky Fung
 */
@Configuration
public class RabbitConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(Constant.EXCHANGE_NAME);
    }

    //Queue，构建队列，名称，是否持久化之类
    @Bean
    public Queue queue() {
        return new Queue(Constant.QUEUE_NAME, true);
    }

    //Binding，将DirectExchange与Queue进行绑定
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(Constant.ROUTING_KEY);
    }

}
