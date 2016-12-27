package com.bytebeats.kafka;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

	private int i = 10;
	
	@Test
	public void testFormat(){
		
		long start = System.nanoTime();
		System.out.println("start:"+start);
		
		System.out.println(String.format("{\"type\":\"marker\", \"t\":%d, \"k\":%d}", System.nanoTime(), i));
		
		long end = System.nanoTime();
		System.out.println("end:"+end);
		System.out.println("cost:"+(end - start));
		
		System.out.println(start * 1e-9);
	}
}
