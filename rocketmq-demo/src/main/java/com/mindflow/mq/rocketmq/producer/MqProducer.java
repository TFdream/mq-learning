package com.mindflow.mq.rocketmq.producer;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class MqProducer {
    private DefaultMQProducer producer;

    public MqProducer(){
        producer = new DefaultMQProducer();
    }

    public void setProducerGroup(String producerGroup) {
        producer.setProducerGroup(producerGroup);
    }

    public void setNamesrvAddr(String addr) {
        producer.setNamesrvAddr(addr);
    }

    public void setMaxMessageSize(int maxMessageSize) {
        producer.setMaxMessageSize(maxMessageSize);
    }

    public void setSendMsgTimeout(int sendMsgTimeout) {
        producer.setSendMsgTimeout(sendMsgTimeout);
    }

    public void start() {
        // 初始化Producer，整个应用生命周期内，只需要初始化1次
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void send() throws Exception {

        for(int i=0; i<100; i++) {
            // 构造Message
            Message message = new Message();
            message.setTopic("TopicA");
            message.setTags("TagA");
            message.setKeys("Key");
            message.setBody("body".getBytes(StandardCharsets.UTF_8));

            // 发送消息并返回结果
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }
    }

    public void shutdown(){
        if(producer!=null){
            // 清理资源，关闭网络连接，注销自己
            producer.shutdown();
        }
    }
}
