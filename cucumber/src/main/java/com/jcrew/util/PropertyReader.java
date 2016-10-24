package com.jcrew.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.String;
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
        String execViewport = System.getProperty("viewport", "phantomjs");    	
        String execUser = System.getProperty("user", "user.1");
        String country = System.getProperty("country", "us");

        FileInputStream inputFile = new FileInputStream("properties/environment.properties");
        properties.load(inputFile);
        inputFile = new FileInputStream("properties/viewport.properties");
        properties.load(inputFile);
        inputFile = new FileInputStream("properties/users.properties");
        properties.load(inputFile);

        properties.setProperty("environment",execEnvironment);
        properties.setProperty("viewport", execViewport);
        properties.setProperty("country", country);
        
        String strURL = properties.getProperty(execEnvironment);
        logger.info("URL to be used {}", strURL);
        properties.setProperty("url", strURL);
        
        if (execViewport.equalsIgnoreCase("phantomjs") | execViewport.equalsIgnoreCase("chrome") | execViewport.equalsIgnoreCase("firefox")) {
        	logger.info("Browser to be used {}", execViewport);
        	properties.setProperty("browser", execViewport);    	
        }
        else {
        	logger.info("Device to be used {}", execViewport);
        	String strBrowser = properties.getProperty(execViewport + ".browser");
        	properties.setProperty("browser", strBrowser);  
        }
         
        logger.info("UserID to be used {}", execUser);
        properties.setProperty("user", execUser);

        logger.info("Country to be used {}", country);
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
            throw new RuntimeException("Property '" + key + "' is not defined in environment,viewport our country file");
        }

        return value;
    }
}
