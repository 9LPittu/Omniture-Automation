package com.jcrew.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestDataReader {

	private static final TestDataReader dataReader = new TestDataReader();
	private final Logger logger = LoggerFactory.getLogger(TestDataReader.class);
	private static final Map<String, Properties> testDataPropertiesMap = new HashMap<>();
	private String environment = System.getProperty("environment", "ci");
	
	private TestDataReader(){
	}

	public static TestDataReader getTestDataReader(){ return dataReader;}

	private Properties loadProperties(){
		
		Properties testDataProperties = new Properties();

        try {
            String testData = "properties/commonTestData.properties";

            FileInputStream propertiesInput = new FileInputStream(testData);
            testDataProperties.load(propertiesInput);
            String environmentData = "properties/environment/" + environment + ".properties";
            propertiesInput = new FileInputStream(environmentData);
            testDataProperties.load(propertiesInput);

            String shipData = "properties/shippingmethod.properties";
            FileInputStream shippingInput = new FileInputStream(shipData);
            testDataProperties.load(shippingInput);
            logger.debug("Shipping data file to be used {}", shipData);

            String country = System.getProperty("country", "us");
            String countryPath = "properties/countries/" + country.toLowerCase() + ".properties";
            propertiesInput = new FileInputStream(countryPath);
            testDataProperties.load(propertiesInput);
            logger.debug("country path: {}", countryPath);

        }  catch (IOException e) {
            logger.debug("Unable to load test data file.", e);
        }

        return testDataProperties;
	}
	
	private Properties loadProperties(String country)  {
        Properties testDataProperties = new Properties();

        try {
            String testData = "properties/commonTestData.properties";

            FileInputStream propertiesInput = new FileInputStream(testData);
            testDataProperties.load(propertiesInput);
            String environmentData = "properties/environment/" + environment + ".properties";
            propertiesInput = new FileInputStream(environmentData);
            testDataProperties.load(propertiesInput);

            String shipData = "properties/shippingmethod.properties";
            FileInputStream shippingInput = new FileInputStream(shipData);
            testDataProperties.load(shippingInput);
            logger.debug("Shipping data file to be used {}", shipData);

            String countryPath = "properties/countries/" + country.toLowerCase() + ".properties";
            propertiesInput = new FileInputStream(countryPath);
            testDataProperties.load(propertiesInput);
            logger.debug("country path: {}", countryPath);

        }  catch (IOException e) {
            logger.debug("Unable to load test data file.", e);
        }

        return testDataProperties;
    }


	 public String getData(String key) {
	        if (!hasProperty(key)) {
	            throw new RuntimeException("Property '" + key + "' is not defined in TestData file");
	        }

	        Properties testDataProperties = getPropertiesForCurrentThread();
	        return testDataProperties.getProperty(key);
	}

	public boolean hasProperty(String key) {
		Properties p = getPropertiesForCurrentThread();
        return p.containsKey(key);
	}

	public String getCountry(String countryGroup) {
		if(countryGroup.equalsIgnoreCase("PRICEBOOK")) {
			countryGroup = "pricebookCountries";
		} else if (countryGroup.equalsIgnoreCase("NON-PRICEBOOK")) {
			countryGroup = "nonPricebookCountries";
		}

		String countries = getData(countryGroup);
		String countriesArray[] = countries.split(";");

		int countryindex = Util.randomIndex(countriesArray.length);

		return countriesArray[countryindex].toLowerCase();
	}

	public int getInt(String key) {
        return Integer.parseInt(getData(key));
    }
	
	public String[] getDataArray(String key) {
		return getData(key).split(";");
	}

	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(getData(key));
	}
	
	public String getSearchWord() {
        String searchWords = getData("search.words");
        String words[] = searchWords.split(";");

        return words[Util.randomIndex(words.length)];
	 }
	 
	 public void updateReader() {
        Properties testDataProperties = getPropertiesForCurrentThread();
        testDataProperties.clear();
        testDataProperties = loadProperties();
        testDataPropertiesMap.put(Thread.currentThread().getName(), testDataProperties);
	 }

	 public void updateReader(String country) {
	     Properties testDataProperties = getPropertiesForCurrentThread();
	     testDataProperties.clear();
	     testDataProperties = loadProperties(country);
	     testDataPropertiesMap.put(Thread.currentThread().getName(), testDataProperties);
	 }

	 private Properties getPropertiesForCurrentThread() {
        Properties properties = testDataPropertiesMap.get(Thread.currentThread().getName());

        if (properties == null) {
            synchronized (testDataPropertiesMap) {
                properties = loadProperties();
                testDataPropertiesMap.put(Thread.currentThread().getName(), properties);
            }
        }
        return properties;
	 }
	    
	 public String getRandomCountry(String group) {
        String country = null;
        int index;

        if ("PRICEBOOK".equals(group)) {
            String pricebookCountries = getData("pricebookCountries");
            String pricebookCountriesArray[] = pricebookCountries.split(";");
            index = Util.randomIndex(pricebookCountriesArray.length);
            country = pricebookCountriesArray[index];	            
        } else if ("NONPRICEBOOK".equals(group)) {
            String nonPricebookCountries = getData("nonPricebookCountries");
            String nonPricebookCountriesArray[] = nonPricebookCountries.split(";");
            index = Util.randomIndex(nonPricebookCountriesArray.length);
            country = nonPricebookCountriesArray[index];
        }
        
        if(country==null){
        	throw new NullPointerException("Failed to select random country for country group '" + group + "' from testdata!!!");
        }

        return country;
	 }
}