package com.bytebeats.mq.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageQueue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2017-04-04 22:52
 */
public class PullConsumerApp {
    private DefaultMQPullConsumer consumer;
    private static final Map<MessageQueue, Long> offseTable = new HashMap<MessageQueue, Long>();

    public PullConsumerApp(String namesrvAddr){
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("consumer");
        consumer.setNamesrvAddr(namesrvAddr);

        try {
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void consume() throws Exception {

        //获取订阅topic的queue
        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicTest1");
        for (MessageQueue mq : mqs) {
            System.out.println("Consume from the queue: " + mq);
            SINGLE_MQ:
            while (true) {
                try {//阻塞的拉去消息，中止时间默认20s
                    PullResult pullResult =
                            consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                    System.out.println(Thread.currentThread().getName()+new Date()+""+pullResult);
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
                        case FOUND://pullSataus
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            break SINGLE_MQ;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offseTable.get(mq);
        if (offset != null)
            return offset;

        return 0;
    }

    private void putMessageQueueOffset(MessageQueue mq, long offset) {
        offseTable.put(mq, offset);
    }
}
