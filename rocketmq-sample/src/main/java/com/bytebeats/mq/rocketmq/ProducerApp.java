package com.bytebeats.mq.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2017-04-04 22:46
 */
public class ProducerApp {
    private DefaultMQProducer producer;

    public ProducerApp(String namesrvAddr){
        // 构造Producer
        producer = new DefaultMQProducer("ProducerGroupName");
        producer.setSendMsgTimeout(1000);
        producer.setNamesrvAddr(namesrvAddr);
        // 初始化Producer，整个应用生命周期内，只需要初始化1次
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void send(String topic, String key, String body) throws Exception {
        // 构造Message
        Message msg = new Message(topic,// topic
                "TagA",// tag：给消息打标签,用于区分一类消息，可为null
                key,// key：自定义Key，可以用于去重，可为null
                body.getBytes());// body：消息内容
        // 发送消息并返回结果
        SendResult sendResult = producer.send(msg);
        System.out.println(sendResult);
    }

    public void shutdown(){
        if(producer!=null){
            // 清理资源，关闭网络连接，注销自己
            producer.shutdown();
        }
    }
}
