package com.jcrew.util;

import java.io.FileInputStream;
import java.util.Properties;

public class TestDataReader {
	
	private Properties testDataProperties = new Properties();
	
	public TestDataReader(){
		try {
			FileInputStream input = new FileInputStream("testData.properties");
			testDataProperties.load(input);
		}		
		catch(Exception e) {			
			e.printStackTrace();
		}		
	}

	public String getData(String key){
		return testDataProperties.getProperty(key, "");
	}
}