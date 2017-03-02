package com.jcrew.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by ngarcia on 10/31/16.
 */
public class CountryReader {
    private final Logger logger = LoggerFactory.getLogger(CountryReader.class);
    private final Properties properties = new Properties();

    public CountryReader(String country) {
        try {
            loadProperties(country);
        } catch (IOException e) {
            logger.error("Unable to load country configuration file.", e);
        }
    }

    private void loadProperties(String country) throws IOException {
        PropertyReader reader = PropertyReader.getPropertyReader();

        FileInputStream inputFile = new FileInputStream("properties/countries/" + country + ".properties");
        properties.load(inputFile);

        inputFile = new FileInputStream("properties/" + reader.getProperty("brand") + "/countries/" + country + ".properties");
        properties.load(inputFile);
    }

    public String getData(String property) {
        return readProperty(property);
    }

    public String getProperty(String property, String defaultValue) {
        if(!hasProperty(property))
            return defaultValue;

        return readProperty(property);
    }

    public boolean hasProperty(String key) {
        return properties.containsKey(key);
    }

    private String readProperty(String key) {
        String value = properties.getProperty(key);

        if (!hasProperty(key)) {
            throw new RuntimeException("Property '" + key + "' is not defined in properties file");
        }

        return value;
    }
}
