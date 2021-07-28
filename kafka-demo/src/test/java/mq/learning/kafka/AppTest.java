package mq.learning.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Duration;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Ignore
	@Test
	public void testStandalone(){
		Properties kafkaProperties = new Properties();
		kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		kafkaProperties.put("bootstrap.servers", "localhost:9092");
		KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(kafkaProperties);
		List<TopicPartition> partitions = new ArrayList<>();
		partitions.add(new TopicPartition("test_topic", 0));
		partitions.add(new TopicPartition("test_topic", 1));
		//
		consumer.assign(partitions);
		while (true) {
			ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(3000));
			for (ConsumerRecord<String, byte[]> record : records) {
				System.out.printf("topic:%s, partition:%s%n", record.topic(), record.partition());
			}
		}
	}

}
