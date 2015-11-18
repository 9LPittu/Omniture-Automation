package com.jcrew.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    private static final int DESKTOP_DEFAULT_WIDTH = 1200;
    private static final int DESKTOP_DEFAULT_HEIGHT = 800;
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
        //String environment = System.getProperty("environment", "ci");
        //String viewport = System.getProperty("viewport", "desktop");                
        
        String environment = System.getProperty("environment", "qa");
        String viewport = System.getProperty("viewport", "mobile-firefox");
        
    	String configurationFile = environment + "-" + viewport + ".properties";

        logger.info("Configuration file to be used {}", configurationFile);

        FileInputStream inputStream = new FileInputStream(configurationFile);
        properties.load(inputStream);
    }

    public String readProperty(String key) {
        return properties.getProperty(key);
    }


    public String getBrowser() {
        return readProperty("browser");
    }

    public String getUserAgent() {
        return readProperty("user.agent");
    }

    public int getWindowWidth() {
        String widthString = readProperty("window.width");
        int width;
        try {
            width = Integer.parseInt(widthString);
        } catch (NumberFormatException nfe) {
            width = DESKTOP_DEFAULT_WIDTH;
        }
        return width;
    }

    public int getWindowHeight() {
        String heightString = readProperty("window.height");
        int height;
        try {
            height = Integer.parseInt(heightString);
        } catch (NumberFormatException nfe) {
            height = DESKTOP_DEFAULT_HEIGHT;
        }
        return height;
    }

    public String getEnvironment() {
        return readProperty("environment");
    }

    public boolean isRemoteExecution() {
        return "true".equals(System.getProperty("remote.execution"));
    }


    public String getSeleniumHubUrl() {
        return readProperty("selenium.grid.hub.url");
    }

    public String getProperty(String property) {
        return readProperty(property);
    }

    public String getScreenshotForEveryStep() {
        return "yes";
    }
}