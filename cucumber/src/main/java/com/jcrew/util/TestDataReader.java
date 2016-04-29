package com.jcrew.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestDataReader {

	private static final TestDataReader dataReader = new TestDataReader();
	private Properties testDataProperties = new Properties();
	private final Logger logger = LoggerFactory.getLogger(TestDataReader.class);
	
	private TestDataReader(){
		try {
			loadProperties();
		} catch (IOException e) {
			logger.debug("Unable to load test data file.");
		}
	}

	public static TestDataReader getTestDataReader(){ return dataReader;}

	private void loadProperties() throws IOException{
		
		String testData = "commonTestData.properties";

		logger.debug("Test Data file to be used {}", testData);

		FileInputStream environmentInput = new FileInputStream(testData);

		testDataProperties.load(environmentInput);

	}

	public String getData(String key){
		String value = testDataProperties.getProperty(key);

		if (!hasProperty(key)) {
			throw new RuntimeException("Property '" + key + "' is not defined in environment or viewport file");
		}

		return value;
	}

	public boolean hasProperty(String key) {
		return testDataProperties.containsKey(key);
	}

	public String getCountry(String countryGroup) {
		if(countryGroup.equalsIgnoreCase("PRICEBOOK")) {
			countryGroup = "pricebookCountries";
		} else if (countryGroup.equalsIgnoreCase("NON-PRICEBOOK")) {
			countryGroup = "nonPricebookCountries";
		}

		String pricebookCountries = getData(countryGroup);
		String pricebookCountriesArray[] = pricebookCountries.split(";");

		int countryindex = Util.randomIndex(pricebookCountriesArray.length);

		return pricebookCountriesArray[countryindex].toLowerCase();
	}
}