package com.ricky.codelab.kafka;

/**
 * Hello world!
 *
 */
public class App {
	
	public static void main(String[] args) {
		
		if (args.length < 1) {
            throw new IllegalArgumentException("Must have either 'producer' or 'consumer' as argument");
        }
        switch (args[0]) {
            case "producer":
                new KafkaProducerDemo().send();
                break;
            case "consumer":
                new KafkaConsumerDemo().consume();
                break;
            default:
                throw new IllegalArgumentException("Don't know how to do " + args[0]);
        }
	}
}
