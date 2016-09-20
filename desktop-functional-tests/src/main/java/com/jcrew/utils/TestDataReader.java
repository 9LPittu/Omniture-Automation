package com.jcrew.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import com.jcrew.pojo.Country;
import com.jcrew.utils.StateHolder;

/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class TestDataReader {
    private static final TestDataReader dataReader = new TestDataReader();
    private static final Map<String, Properties> testDataPropertiesMap = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(TestDataReader.class);
    private StateHolder stateHolder = StateHolder.getInstance();

    private TestDataReader() {
    }

    public static TestDataReader getTestDataReader() {
        return dataReader;
    }

    private Properties loadProperties()  {
        Properties testDataProperties = new Properties();

        try {
            String testData = "properties/TestData.properties";

            FileInputStream propertiesInput = new FileInputStream(testData);
            testDataProperties.load(propertiesInput);

            String env = System.getProperty("environment", "ci");
            String environmentData = "properties/environment/" + env+ ".properties";

            propertiesInput = new FileInputStream(environmentData);
            testDataProperties.load(propertiesInput);

            String country = System.getProperty("country", "us");

            if (stateHolder.hasKey("context")) {
                Country c = stateHolder.get("context");
                country = c.getCountry();
                logger.info("Found country in context {}", country);
            }

            String countryPath = "properties/countries/" + country + ".properties";
            logger.debug("country path: {}", countryPath);
            propertiesInput = new FileInputStream(countryPath);
            testDataProperties.load(propertiesInput);
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

    public int getInt(String key) {
        return Integer.parseInt(getData(key));
    }

    public String[] getDataArray(String key) {
        return getData(key).split(";");
    }

    public boolean hasProperty(String key) {
        Properties p = getPropertiesForCurrentThread();
        return p.containsKey(key);
    }

    public String getCategory() {
        String mainCategories = getData("main.categories");
        String categories[] = mainCategories.split(";");

        return categories[Util.randomIndex(categories.length)];
    }

    public String getSubCategorySection(String category) {
        String subCategorySections = getData("sub."+category.toLowerCase());
        String subCategorySectionNames[] = subCategorySections.split(";");
        return subCategorySectionNames[Util.randomIndex(subCategorySectionNames.length)];
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

    public void updateReader() {
        Properties testDataProperties = getPropertiesForCurrentThread();
        testDataProperties.clear();
        testDataProperties = loadProperties();
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
}
