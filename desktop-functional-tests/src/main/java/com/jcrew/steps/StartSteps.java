package com.jcrew.steps;

import com.jcrew.pojo.Country;
import com.jcrew.utils.*;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class StartSteps {
    private final Logger logger = LoggerFactory.getLogger(StartSteps.class);
    private final PropertyReader reader = PropertyReader.getPropertyReader();
    private final StateHolder stateHolder = StateHolder.getInstance();
    private DriverFactory driverFactory;
    private WebDriver driver;

    @Before
    public void setupDriver() throws IOException {
        driverFactory = new DriverFactory();
        driver = driverFactory.getDriver();

    }

    @Given("User goes to homepage")
    public void user_is_on_home_page() {
        int retry = 0;
        boolean successfulLoad = false;
        while (retry < 2 && !successfulLoad) {
            try {
                getHomePage();
                waitForHeaderPromo();
                successfulLoad = true;
            } catch (TimeoutException te) {
                logger.debug("Page did not load retry: {}", retry + 1);
                retry++;
            }
        }
    }

    @Given("User goes to international homepage for ([^\"]*)")
    public void user_goes_to_international_homepage(String group) {
        TestDataReader testData = TestDataReader.getTestDataReader();
        String country = testData.getRandomCountry(group);

        int retry = 0;
        boolean successfulLoad = false;
        while (retry < 2 && !successfulLoad) {
            try {
                getHomePage(country);
                waitForHeaderPromo();
                successfulLoad = true;
            } catch (TimeoutException te) {
                logger.debug("Page did not load retry: {}", retry + 1);
                retry++;
            }
        }
    }

    @Given("User lands on international page from list for ([^\"]*)")
    public void user_goes_lands_on_international(String group, List<String> pageList) {
        String page = pageList.get(Util.randomIndex(pageList.size()));
        page = page.toLowerCase();
        TestDataReader testData = TestDataReader.getTestDataReader();

        String pageURL = testData.getData("page."+page);
        String country = testData.getRandomCountry(group);

        int retry = 0;
        boolean successfulLoad = false;
        while (retry < 2 && !successfulLoad) {
            try {
                getInternationalPage(pageURL, country);
                waitForHeaderPromo();
                successfulLoad = true;
            } catch (TimeoutException te) {
                logger.debug("Page did not load retry: {}", retry + 1);
                retry++;
            }
        }
    }

    private void getInternationalPage(String pageURL, String country) {
        String envUrl = reader.getProperty("url");
        Country countrySettings = new Country(envUrl, country);
        stateHolder.put("context", countrySettings);

        String homeURL = countrySettings.getHomeurl();
        String intlPageURL = homeURL + pageURL;

        logger.debug("getting url: " + intlPageURL);
        driver.get(intlPageURL);
    }

    private void waitForHeaderPromo() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.id("global__promo")));
    }

    private void getHomePage() {
        String country = reader.getProperty("country");
        String envUrl = reader.getProperty("url");

        Country context = new Country(envUrl, country);
        stateHolder.put("context", context);
        envUrl = context.getHomeurl();

        logger.debug("getting url: " + envUrl);
        driver.get(envUrl);
    }

    private void getHomePage(String country) {
        String envUrl = reader.getProperty("url");

        Country context = new Country(envUrl, country);
        stateHolder.put("context", context);
        envUrl = context.getHomeurl();

        logger.debug("getting url: " + envUrl);
        driver.get(envUrl);
    }
    
    @And("user should see country code in the url for international countries")
    public void user_should_see_country_code_in_url_for_international_countries(){


        Country c = (Country)stateHolder.get("context");
    	String env = reader.getProperty("url");


    	assertTrue("Country code '" + c.getCountry() + "' should be displayed in the url except United States",
    			Util.createWebDriverWait(driver).until(ExpectedConditions.urlMatches(c.getHomeurl())));
    }
}
