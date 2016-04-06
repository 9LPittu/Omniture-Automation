package com.jcrew.util;

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
        String environment = System.getProperty("environment", "ci");
        String viewport = System.getProperty("viewport", "desktop");
        String country = System.getProperty("country", "us");
        

    	String environmentFile = environment + ".properties";
        String viewportFile = viewport + ".properties";

        logger.info("Environment configuration file to be used {}", environmentFile);
        logger.info("Viewport configuration file to be used {}", viewportFile);

        FileInputStream countriesInput = new FileInputStream("countries.properties");
        properties.load(countriesInput);

        FileInputStream environmentInput = new FileInputStream(environmentFile);
        properties.load(environmentInput);

        FileInputStream viewportInput = new FileInputStream(viewportFile);
        properties.load(viewportInput);
    }

    public boolean isSystemPropertyTrue(String key) {
        return "true".equals(System.getProperty(key));
    }

    public String getProperty(String property) {
        return readProperty(property);
    }

    public boolean hasProperty(String key) {
        return properties.containsKey(key);
    }

    private String readProperty(String key) {
        String value = properties.getProperty(key);

        if (!hasProperty(key)) {
            throw new RuntimeException("Property '" + key + "' is not defined in environment,viewport our country file");
        }

        return value;
    }
}
