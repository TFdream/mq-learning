package com.ricky.codelab.kafka.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class PropertyUtils {

	private PropertyUtils(){
		
	}
	
	public static Properties load(File file) throws IOException{
		
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			Properties props = new Properties();
			props.load(in);
			
			return props;
			
		}finally{
			IOUtils.closeQuietly(in);
		}
	}
	
	public static Properties load(String path) throws IOException{
		
		InputStream in = null;
		try {
			in = PropertyUtils.class.getClassLoader().getResourceAsStream(path);
			Properties props = new Properties();
			props.load(in);
			
			return props;
			
		}finally{
			IOUtils.closeQuietly(in);
		}
	}
}
