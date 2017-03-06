package com.jcrew.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class UserReader {
    private static final UserReader propertyReader = new UserReader();
    private final Properties properties = new Properties();
    private final Logger logger = LoggerFactory.getLogger(PropertyReader.class);

    private UserReader() {
        try {
            loadProperties();
        } catch (IOException e) {
            logger.error("Unable to load configuration file.");
        }
    }

    public static UserReader getUserReader() {
        return propertyReader;
    }

    private void loadProperties() throws IOException {
        PropertyReader reader = PropertyReader.getPropertyReader();
        String execEnvironment = reader.getProperty("environment");
        String brand = reader.getProperty("brand");
        String execUser = reader.getProperty("user");

        FileInputStream inputFile = new FileInputStream("properties/" + brand + "/users.properties");
        properties.load(inputFile);

        properties.setProperty("user", execEnvironment + "." + execUser);
        properties.setProperty("noDefaultUser", execEnvironment + ".nodefault");
        properties.setProperty("multiple", execEnvironment + ".multiple");
        properties.setProperty("noDefaultMultiple", execEnvironment + ".nodefaultmultiple");
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
