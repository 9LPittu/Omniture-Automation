package com.jcrew.util.context;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by nadiapaolagarcia on 4/6/16.
 */
public class CountryContext {
    private static final Map<String, CountryContext> contextMap = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(CountryContext.class);
    private String country;
    private String currency;
    private String contexturl;

    private CountryContext(String country) {
        try {

            Properties properties = new Properties();
            FileInputStream countriesInput = new FileInputStream("countries.properties");
            properties.load(countriesInput);

            currency = properties.getProperty(country + ".currency");
            if (currency == null) {
                logger.error("Not able to find currency for country {}, assuming $");
                currency = "$";
            }

            contexturl = properties.getProperty(country + ".contexturl");
            if (contexturl == null) {
                logger.error("Not able to find contexturl for country {}, assuming false");
                contexturl = "false";
            }

        } catch (IOException e) {
            logger.error("Unable to load country context file.");
        }
    }

    public CountryContext getContext() {
        String identifier = Thread.currentThread().getName();
        CountryContext context = contextMap.get(identifier);

        if (context == null) {
            context = new CountryContext("us");
            contextMap.put(identifier, context);
        }

        return context;
    }

    public void setContext(String country) {
        String identifier = Thread.currentThread().getName();
        CountryContext context = new CountryContext(country);

        contextMap.put(identifier, context);
    }

    public void initPage(WebDriver driver, Object objectClass) throws CountryContextException {
        String url = driver.getCurrentUrl();
        boolean countryContext = Boolean.parseBoolean(contexturl);

        if (countryContextURLCompliance(url, countryContext, country))
            throw new CountryContextException("The URL " + url + " is not valid for country " + country);

        PageFactory.initElements(driver, objectClass);
    }

    private static boolean countryContextURLCompliance(String url, boolean mustHaveCountryCode, String code) {
        String context = "/" + code + "/";
        boolean contains = url.contains(context);

        return contains == mustHaveCountryCode;
    }

}
