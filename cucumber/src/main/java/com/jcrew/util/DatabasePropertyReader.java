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
        FileInputStream databaseInput = new FileInputStream("databaseconnection.properties");
        databaseProperties.load(databaseInput);
        databaseInput = new FileInputStream("databasequeries.properties");
        databaseProperties.load(databaseInput);

        String execEnvironment = System.getProperty("environment", "ci");

        String dbSchemaName = System.getProperty(execEnvironment + ".schema");
        databaseProperties.setProperty("schema", dbSchemaName);

        if (execEnvironment.equalsIgnoreCase("steel") || execEnvironment.equalsIgnoreCase("titanium") || execEnvironment.equalsIgnoreCase("platimun"))
            execEnvironment = "jcms";
        databaseProperties.setProperty("dbEnvironment", execEnvironment);
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
            throw new RuntimeException("Property '" + key + "' is not defined in databaseconnection or databasequeries file");
        }

        return value;
    }
}
