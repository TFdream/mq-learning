package mq.learning.kafka.mq.raw;

import java.io.IOException;
import java.util.Properties;

import mq.learning.kafka.util.PropertyUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class NativeKafkaProducer {

	private int total = 1000000;
	
	public static void main(String[] args) {
		
		new NativeKafkaProducer().send();
	}

	public void send(){
		
		long start = System.currentTimeMillis();
		System.out.println("Kafka Producer send msg start,total msgs:"+total);
		
		// set up the producer
		Producer<String, String> producer = null;
		try {
			Properties props = PropertyUtils.load("producer_config.properties");
			//配置分区策略
			props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "mq.learning.kafka.partition.MyPartitioner");
			producer = new KafkaProducer<>(props);
			
			for (int i = 0; i < total; i++){
				producer.send(new ProducerRecord<String, String>("hellworld",
						String.valueOf(i), String.format("{\"type\":\"test\", \"t\":%d, \"k\":%d}", System.currentTimeMillis(), i)), (metadata, e)->{if (e!=null) {
					System.out.println("send error:"+e.getMessage());
				}});
				
				// every so often send to a different topic
                if (i % 1000 == 0) {
                	producer.send(new ProducerRecord<String, String>("test", String.format("{\"type\":\"marker\", \"t\":%d, \"k\":%d}", System.currentTimeMillis(), i)));
                	producer.send(new ProducerRecord<String, String>("hellworld", String.format("{\"type\":\"marker\", \"t\":%d, \"k\":%d}", System.currentTimeMillis(), i)));
                	
                	producer.flush();
                    System.out.println("Sent msg number " + i);
                }
                
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			producer.close();
		}
		
		System.out.println("Kafka Producer send msg over,cost time:"+(System.currentTimeMillis()-start)+"ms");
	}
}
