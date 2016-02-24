package com.ricky.codelab.kafka;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.ricky.codelab.kafka.util.PropertyUtils;

public class KafkaProducerDemo {

	private int total = 1000000;
	
	public static void main(String[] args) {
		
		new KafkaProducerDemo().send();
	}

	public void send(){
		
		long start = System.currentTimeMillis();
		System.out.println("Kafka Producer send msg start,total msgs:"+total);
		
		// set up the producer
		Producer<String, String> producer = null;
		try {
			Properties props = PropertyUtils.load("producer_config.properties");
			producer = new KafkaProducer<>(props);
			
			for (int i = 0; i < total; i++){
				producer.send(new ProducerRecord<String, String>("hello",
						String.valueOf(i), String.format("{\"type\":\"test\", \"t\":%d, \"k\":%d}", System.currentTimeMillis(), i)));
				
				// every so often send to a different topic
                if (i % 1000 == 0) {
                	producer.send(new ProducerRecord<String, String>("test", String.format("{\"type\":\"marker\", \"t\":%d, \"k\":%d}", System.currentTimeMillis(), i)));
                	producer.send(new ProducerRecord<String, String>("hello", String.format("{\"type\":\"marker\", \"t\":%d, \"k\":%d}", System.currentTimeMillis(), i)));
                	
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
