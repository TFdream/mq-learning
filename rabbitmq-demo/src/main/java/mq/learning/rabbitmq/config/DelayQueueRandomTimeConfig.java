package mq.learning.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 一种更通用的方案才能满足需求，那么就只能将TTL设置在消息属性里了
 * @author Ricky Fung
 */
@Configuration
public class DelayQueueRandomTimeConfig {

    //=======业务队列
    public static final String DELAY_EXCHANGE_NAME = "demo.delay_queue.random.biz.exchange";
    public static final String DELAY_QUEUE_NAME = "demo.delay_queue.random.biz.queue";
    public static final String DELAY_QUEUE_ROUTING_KEY = "random";

    //=======死信队列
    public static final String DEAD_LETTER_EXCHANGE = "demo.delay_queue.random.dead_letter.exchange";
    public static final String DEAD_LETTER_QUEUE_NAME = "demo.delay_queue.random.dead_letter.queue";
    public static final String DEAD_LETTER_QUEUE_ROUTING_KEY = "random";

    //声明延时队列C 不设置TTL
    @Bean
    public Queue delayQueueRandom() {
        Map<String, Object> args = new HashMap<>(4);
        // x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // x-dead-letter-routing-key  这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_ROUTING_KEY);
        return QueueBuilder.durable(DELAY_QUEUE_NAME).withArguments(args).build();
    }

    // 声明死信队列用于接收延时60s处理的消息
    @Bean
    public Queue deadLetterQueueRandom(){
        return new Queue(DEAD_LETTER_QUEUE_NAME);
    }

    // 声明延时Exchange
    @Bean
    public DirectExchange delayExchangeRandom(){
        return new DirectExchange(DELAY_EXCHANGE_NAME);
    }

    // 声明死信Exchange
    @Bean
    public DirectExchange deadLetterExchangeRandom(){
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    // 声明延时队列绑定关系
    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueueRandom()).to(delayExchangeRandom()).with(DELAY_QUEUE_ROUTING_KEY);
    }

    // 声明死信队列绑定关系
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueueRandom()).to(deadLetterExchangeRandom()).with(DEAD_LETTER_QUEUE_ROUTING_KEY);
    }
}
