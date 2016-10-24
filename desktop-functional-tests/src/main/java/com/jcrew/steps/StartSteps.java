package com.jcrew.steps;

import com.jcrew.pojo.Country;
import com.jcrew.utils.*;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import cucumber.api.Scenario;



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
    public void setupDriver(Scenario scenario) throws IOException {
        String scenarioName = scenario.getName();
        stateHolder.put("scenarioName", scenarioName);
        
        stateHolder.put("deletecookies", false);
        
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

    @Given("^User is on homepage with clean session$")
    public void user_is_on_home_page_with_clean_session() {
        driverFactory.deleteBrowserCookies();
        user_is_on_home_page();
    }
    @Given("^User is on homepage with siteid$")
    public void user_is_on_home_page_with_siteid(){
        driverFactory.deleteBrowserCookies();
        int retry = 0;
        boolean successfulLoad = false;
        while (retry < 2 && !successfulLoad) {
            try {
                getHomePageWithSideID();
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
        driverFactory.deleteBrowserCookies();
        int retry = 0;
        boolean successfulLoad = false;
        while (retry < 2 && !successfulLoad) {
            try {
                getIntlHomePage(country);
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

        //Update Reader and create context
        TestDataReader reader = TestDataReader.getTestDataReader();
        reader.updateReader(country);

        Country countrySettings = new Country(envUrl, country);
        stateHolder.put("context", countrySettings);

        String homeURL = countrySettings.getHomeurl();
        String intlPageURL = homeURL +"/"+ countrySettings + "/" + pageURL;
        logger.debug("getting url: " + intlPageURL);

        setSidecarCookie();

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

        setSidecarCookie();

        driver.get(envUrl);
    }

    private void getIntlHomePage(String country) {
        String envUrl = reader.getProperty("url");

        //Update Reader and create context
        TestDataReader reader = TestDataReader.getTestDataReader();
        reader.updateReader(country);
        Country context = new Country(envUrl, country);
        stateHolder.put("context", context);

        envUrl = context.getHomeurl();

        setSidecarCookie();

        String intlHomeURL = envUrl +"/"+ context + "/" ;
        logger.debug("getting url: " + intlHomeURL);
        driver.get(intlHomeURL);
    }
    private void getHomePageWithSideID(){
        String country = reader.getProperty("country");
        String envUrl = reader.getProperty("url");

        Country context = new Country(envUrl, country);
        stateHolder.put("context", context);
        envUrl = context.getHomeurl();
        envUrl=envUrl + "?siteId=asdfsadf&srcCode=asdfsadf";
        logger.debug("getting url: " + envUrl);

        setSidecarCookie();

        driver.get(envUrl);
    }
    
    private void setSidecarCookie() {
    	TestDataReader testdataReader = TestDataReader.getTestDataReader();
    	boolean setCookie = testdataReader.getBoolean("setSidecarCookie");
    	if(setCookie) {
    		String url = reader.getProperty("url");
    		String domain = url.replace("https://", "");
            driver.get(url + "/404");
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("document.cookie=\"x-origin=sidecar_render;path=/;domain=" + domain + ";expires=new Date().setDate(new Date().getDate() + 1) \"");
            logger.info("Setting sidecar cookie as: {}", "document.cookie=\"x-origin=sidecar_render;path=/;domain=" + domain + ";expires=new Date().setDate(new Date().getDate() + 1) \"");
        }
    	
    }
}
