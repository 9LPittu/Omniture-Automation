package com.jcrew.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    private static final PropertyReader propertyReader = new PropertyReader();
    private final Properties properties = new Properties();
    private final Logger logger = LoggerFactory.getLogger(PropertyReader.class);

    private PropertyReader() {
        try {
            loadProperties();
        } catch (IOException e) {
            logger.error("Unable to load configuration file.");
        }
    }

    public static PropertyReader getPropertyReader() {
        return propertyReader;
    }

    private void loadProperties() throws IOException {
        String execEnvironment = Util.getEnvironment();
        String execViewport = System.getProperty("viewport", "chrome");
        String execUser = System.getProperty("user", "user.1");
        String country = System.getProperty("country", "us");
        String brand = System.getProperty("brand", "jcrew");

        FileInputStream inputFile = new FileInputStream("properties/environment.properties");
        properties.load(inputFile);
        inputFile = new FileInputStream("properties/viewport.properties");
        properties.load(inputFile);

        properties.setProperty("environment", execEnvironment);

        execEnvironment = properties.getProperty(brand + "." + execEnvironment);
        logger.info("URL to be used {}", execEnvironment);
        properties.setProperty("url", execEnvironment);

        logger.info("Browser to be used {}", execViewport);
        properties.setProperty("browser", execViewport);

        logger.info("UserID to be used {}", execUser);
        properties.setProperty("user", execUser);

        logger.info("Country to be used {}", country);
        properties.setProperty("country", country);

        logger.info("Brand under test {}", brand);
        properties.setProperty("brand", brand);
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
