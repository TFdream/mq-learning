package mq.tutorials.rocketmq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Ricky Fung
 * @version 1.0
 * @since 2018-07-04 23:13
 */
public abstract class PropertyUtils {

	public static Properties load(File file) throws IOException{
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			return load(in);
		}finally{
			IoUtils.closeQuietly(in);
		}
	}
	
	public static Properties load(String path) throws IOException{
		
		InputStream in = null;
		try {
			in = PropertyUtils.class.getClassLoader().getResourceAsStream(path);
			return load(in);
		}finally{
			IoUtils.closeQuietly(in);
		}
	}

	public static Properties load(InputStream in) throws IOException{
		Properties props = new Properties();
		props.load(in);
		return props;
	}
}
