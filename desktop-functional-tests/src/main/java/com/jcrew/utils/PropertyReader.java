package com.jcrew.utils;

import com.jcrew.pojo.User;
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
        String execEnvironment = System.getProperty("environment", "ci");
        String execViewport = System.getProperty("viewport", "desktop");
        String execUser = System.getProperty("user", "user.1");
        String country = System.getProperty("country", "us");

        FileInputStream inputFile = new FileInputStream("environment.properties");
        properties.load(inputFile);
        inputFile = new FileInputStream("viewport.properties");
        properties.load(inputFile);
        inputFile = new FileInputStream("users.properties");
        properties.load(inputFile);
        inputFile = new FileInputStream("countries.properties");
        properties.load(inputFile);

        properties.setProperty("environment", execEnvironment);

        execEnvironment = properties.getProperty(execEnvironment);
        logger.info("URL to be used {}", execEnvironment);
        properties.setProperty("url", execEnvironment);

        logger.info("Browser to be used {}", execViewport);
        properties.setProperty("browser", execViewport);

        logger.info("UserID to be used {}", execUser);
        properties.setProperty("userID", execUser);

        logger.info("Country to be used {}", execUser);
        properties.setProperty("country", country);

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
