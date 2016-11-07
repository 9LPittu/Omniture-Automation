package com.jcrew.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UserReader {
    private static final UserReader propertyReader = new UserReader();
    private final Properties properties = new Properties();
    private final Logger logger = LoggerFactory.getLogger(UserReader.class);

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
        String execUser = reader.getProperty("user");

        FileInputStream inputFile = new FileInputStream("properties/users.properties");
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
