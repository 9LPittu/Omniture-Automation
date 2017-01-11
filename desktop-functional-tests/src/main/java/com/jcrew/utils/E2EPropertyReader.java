package com.jcrew.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class E2EPropertyReader {

    private static final E2EPropertyReader e2ePropertyReader = new E2EPropertyReader();
    private final Properties properties = new Properties();
    private final Logger logger = LoggerFactory.getLogger(E2EPropertyReader.class);

    private E2EPropertyReader() {
        try {
            loadProperties();
        } catch (IOException e) {
            logger.error("Unable to load configuration file.");
        }
    }

    public static E2EPropertyReader getPropertyReader() {
        return e2ePropertyReader;
    }

    private void loadProperties() throws IOException {        
        FileInputStream propertiesFile = null;
        propertiesFile = new FileInputStream("properties/e2e.properties");
        properties.load(propertiesFile);
    }

    public boolean isSystemPropertyTrue(String key) {
        return "true".equals(System.getProperty(key));
    }

    public String getProperty(String property) {
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
