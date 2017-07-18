package com.mindflow.mq.rocketmq.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class MqPushConsumer {
    private DefaultMQPushConsumer consumer;

    public MqPushConsumer() {
        consumer = new DefaultMQPushConsumer();
    }

    public void setConsumerGroup(String consumerGroup) {
        consumer.setConsumerGroup(consumerGroup);
    }

    public void setNamesrvAddr(String addr) {
        consumer.setNamesrvAddr(addr);
    }

    public void setConsumeThreadMax(int maxThreadNum) {
        consumer.setConsumeThreadMax(maxThreadNum);
    }

    public void setConsumeThreadMin(int minThreadNum) {
        consumer.setConsumeThreadMin(minThreadNum);
    }

    public void start() {
        try {
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void consume(String topic, String subExpression) throws MQClientException {

        consumer.subscribe(topic, "*");
//        consumer.subscribe(topic, "TagA || TagB");
//        consumer.subscribe(topic, "*");

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                for(MessageExt msg : msgs) {
                    if (msg.getTags() != null && msg.getTags().equals("TagA")) {
                        // 执行TagA的消费
                    }
                    else if (msg.getTags() != null && msg.getTags().equals("TagB")) {
                        // 执行TagB的消费
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

    }

    public void destroy() {
        consumer.shutdown();
    }
}
