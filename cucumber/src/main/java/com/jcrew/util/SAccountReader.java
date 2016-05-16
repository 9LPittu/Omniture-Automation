package com.jcrew.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.String;
import java.util.Properties;

public class SAccountReader {

    private static final SAccountReader saccountreader = new SAccountReader();
    private final Properties saccountproperties = new Properties();
    private final Logger logger = LoggerFactory.getLogger(SAccountReader.class);

    private SAccountReader() {
        try {
            loadProperties();
        } catch (IOException e) {
            logger.error("Unable to load configuration file.");
        }
    }

    public static SAccountReader getPropertyReader() {
        return saccountreader;
    }

    private void loadProperties() throws IOException {
        FileInputStream inputFile = new FileInputStream("saccount.properties");
        saccountproperties.load(inputFile);
    }

    public String getProperty(String property) {
        return readProperty(property);
    }

    public boolean hasProperty(String key) {
        return saccountproperties.containsKey(key);
    }

    private String readProperty(String key) {
        String value = saccountproperties.getProperty(key);

        if (!hasProperty(key)) {
            throw new RuntimeException("Property '" + key + "' is not defined in saccount file");
        }

        return value;
    }
}
