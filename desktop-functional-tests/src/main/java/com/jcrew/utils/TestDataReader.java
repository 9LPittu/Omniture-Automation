package com.jcrew.utils;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class TestDataReader {
    private static final TestDataReader dataReader = new TestDataReader();
    private Properties testDataProperties = new Properties();
    private final Logger logger = LoggerFactory.getLogger(TestDataReader.class);

    private TestDataReader() {
        try {
            loadProperties();
        } catch (IOException e) {
            logger.debug("Unable to load test data file.");
        }
    }

    public static TestDataReader getTestDataReader() {
        return dataReader;
    }

    private void loadProperties() throws IOException {
        String testData = "TestData.properties";

        FileInputStream environmentInput = new FileInputStream(testData);
        testDataProperties.load(environmentInput);
    }

    public String getData(String key) {
        String value = testDataProperties.getProperty(key);

        if (!hasProperty(key)) {
            throw new RuntimeException("Property '" + key + "' is not defined in environment or viewport file");
        }

        return value;
    }

    private boolean hasProperty(String key) {
        return testDataProperties.containsKey(key);
    }

    public List<String> getMainCategories() {
        String mainCategories = getData("main.categories");

        return Arrays.asList(mainCategories.split(";"));
    }

    public String getRandomCountry(String group) {
        String country = "US";
        int index;

        if ("PRICEBOOK".equals(group)) {
            String pricebookCountries = getData("pricebookCountries");
            String pricebookCountriesArray[] = pricebookCountries.split(";");
            index = Util.randomIndex(pricebookCountriesArray.length);

            country = pricebookCountriesArray[index];
        } else if ("NON-PRICEBOOK".equals(group)) {
            String nonPricebookCountries = getData("nonPricebookCountries");
            String nonPricebookCountriesArray[] = nonPricebookCountries.split(";");
            index = Util.randomIndex(nonPricebookCountriesArray.length);

           country = nonPricebookCountriesArray[index];
        }

        return country;
    }

    public String getSearchWord() {
        String searchWords = getData("search.words");
        String words[] = searchWords.split(";");

        return words[Util.randomIndex(words.length)];
    }
}
