package com.jcrew.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabasePropertyReader {

    private static final DatabasePropertyReader databasePropertyReader = new DatabasePropertyReader();
    private final Properties databaseProperties = new Properties();
    private final Logger logger = LoggerFactory.getLogger(DatabasePropertyReader.class);

    private DatabasePropertyReader() {
        try {
            loadProperties();
        } catch (IOException e) {
            logger.error("Unable to load database property file.");
        }
    }

    public static DatabasePropertyReader getPropertyReader() {
        return databasePropertyReader;
    }

    private void loadProperties() throws IOException {
        String dbProperties = System.getProperty("database", "jcdpdatabase");
    	String dbPropertiesFile = dbProperties + ".properties";

        logger.info("Database property file to be used {}", dbPropertiesFile);

        FileInputStream databaseInput = new FileInputStream(dbPropertiesFile);
        databaseProperties.load(databaseInput);
    }

    public String getProperty(String property) {
        return readProperty(property);
    }

    public boolean hasProperty(String key) {
        return databaseProperties.containsKey(key);
    }

    private String readProperty(String key) {
        String value = databaseProperties.getProperty(key);

        if (!hasProperty(key)) {
            throw new RuntimeException("Property '" + key + "' is not defined in environment or viewport file");
        }

        return value;
    }
}
