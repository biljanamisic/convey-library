package com.convey.client.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class PropertiesUtil {

	private static Logger logger = Logger.getLogger(PropertiesUtil.class);
	private static Properties properties;
	
	static {		
		properties = new Properties();
		InputStream is = null;		
		try {
			is = new FileInputStream("src/main/resources/etc/convey-config.properties");
			properties.load(is); 
		} catch (Exception e) {
			logger.log(Priority.ERROR, "Failed to load convey-config.properties file");
			logger.log(Priority.ERROR, e.getMessage()); 
		}	
	}
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public static Integer getIntProperty(String key) {
		Integer intProperty = null;
		try {
			intProperty = Integer.valueOf(properties.getProperty(key));
			return intProperty;
		} catch (Exception ex) {
			logger.log(Priority.ERROR, "Property " + key + " must be of integer type");
			return intProperty;
		}
	}
}
