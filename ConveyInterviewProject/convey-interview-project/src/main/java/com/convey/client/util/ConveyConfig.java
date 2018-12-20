package com.convey.client.util;

public class ConveyConfig {
	
	public static final String MAX_NUMBER_OF_SAVING_BOOKS_IN_ONE_REQUEST = "maxNumberOfSavingBooksInOneRequest";
	
	public static Integer getProperty(String key) {
		return PropertiesUtil.getIntProperty(key);
	}
}
