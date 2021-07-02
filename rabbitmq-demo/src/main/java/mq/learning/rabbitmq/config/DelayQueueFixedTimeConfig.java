package mq.learning.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ricky Fung
 */
@Configuration
public class DelayQueueFixedTimeConfig {

    //=======业务队列
    public static final String DELAY_EXCHANGE_NAME = "demo.delay_queue.fixed.biz.exchange";
    public static final String DELAY_QUEUE_NAME = "demo.delay_queue.fixed.biz.queue";
    public static final String DELAY_QUEUE_ROUTING_KEY = "fixed";

    //=======死信队列
    public static final String DEAD_LETTER_EXCHANGE = "demo.delay_queue.fixed.dead_letter.exchange";
    public static final String DEAD_LETTER_QUEUE_NAME = "demo.delay_queue.fixed.dead_letter.queue";
    public static final String DEAD_LETTER_QUEUE_ROUTING_KEY = "fixed";

    @Bean
    public Queue delayQueueFT(){
        Map<String, Object> args = new HashMap<>(4);
        // x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // x-dead-letter-routing-key  这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_ROUTING_KEY);
        // x-message-ttl  声明队列的TTL 延时60s
        args.put("x-message-ttl", 60000);
        return QueueBuilder.durable(DELAY_QUEUE_NAME).withArguments(args).build();
    }

    // 声明死信队列 用于接收延时60s处理的消息
    @Bean
    public Queue deadLetterQueueFT(){
        return new Queue(DEAD_LETTER_QUEUE_NAME);
    }

    // 声明延时Exchange
    @Bean
    public DirectExchange delayExchangeFT(){
        return new DirectExchange(DELAY_EXCHANGE_NAME);
    }

    // 声明死信Exchange
    @Bean
    public DirectExchange deadLetterExchangeFT(){
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    // 声明延时队列绑定关系
    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueueFT()).to(delayExchangeFT()).with(DELAY_QUEUE_ROUTING_KEY);
    }

    // 声明死信队列绑定关系
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueueFT()).to(deadLetterExchangeFT()).with(DEAD_LETTER_QUEUE_ROUTING_KEY);
    }
}
