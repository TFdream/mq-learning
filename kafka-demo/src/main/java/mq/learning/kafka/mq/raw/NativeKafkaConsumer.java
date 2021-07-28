package mq.learning.kafka.mq.raw;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import mq.learning.kafka.util.PropertyUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NativeKafkaConsumer {

	public static void main(String[] args) {
		
		new NativeKafkaConsumer().consume();
	}

	public void consume() {
		
		JsonParser jsonParser = new JsonParser();
		
		// and the consumer
		KafkaConsumer<String, String> consumer = null;
		try {
			Properties props = PropertyUtils.load("consumer_config.properties");
			consumer = new KafkaConsumer<>(props);
			
			//subscribe topics
			consumer.subscribe(Arrays.asList("hellworld", "test"));
			
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
				for (ConsumerRecord<String, String> record : records){
					
//					System.out.printf("offset -> %d, key -> %s, value -> %s",
//							record.offset(), record.key(), record.value());
					
					switch (record.topic()) {
						case "hellworld":
						
							JsonObject jObj = (JsonObject)jsonParser.parse(record.value()); 
							switch (jObj.get("type").getAsString()) {
							case "test":
								
								long latency = System.currentTimeMillis() - jObj.get("t").getAsLong();
								System.out.println(latency);
								
								break;
							case "marker":
								
								break;
							default:
								break;
							}
						break;
						case "test":
							
							break;
						default:
							throw new IllegalStateException("Shouldn't be possible to get message on topic " + record.topic());
					}
				}
				//手动提交位移
				consumer.commitSync();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(consumer!=null){
				consumer.close();
			}
		}
	}
}
