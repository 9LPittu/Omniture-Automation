package com.jcrew.steps;

import com.jcrew.pojo.Country;
import com.jcrew.utils.*;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import cucumber.api.Scenario;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class StartSteps {
    private final Logger logger = LoggerFactory.getLogger(StartSteps.class);
    private final PropertyReader reader = PropertyReader.getPropertyReader();
    private final TestDataReader testData = TestDataReader.getTestDataReader();
    private final StateHolder stateHolder = StateHolder.getInstance();
    private WebDriver driver;

    @Before
    public void setupDriver(Scenario scenario){
        stateHolder.put("scenarioName", scenario.getName());
        stateHolder.put("deletecookies", false);

        DriverFactory driverFactory = new DriverFactory();
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
    
    @Given("^User is on homepage with siteid$")
    public void user_is_on_home_page_with_siteid(){
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
        String country = testData.getRandomCountry(group);
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
    
    @Given("User goes to international homepage with country as ([^\"]*)")
    public void user_goes_to_international_homepage_with_country(String country) {
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

    @Given("User lands on international page from below list for ([^\"]*) country")
    public void user_goes_lands_on_international_country(String country, List<String> pageList) {
        String page = pageList.get(Util.randomIndex(pageList.size()));
        page = page.toLowerCase();

        String pageURL = testData.getData("page."+page);        

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
        Country countrySettings = new Country(envUrl, country);
        stateHolder.put("context", countrySettings);

        testData.updateReader();

        String homeURL = countrySettings.getHomeurl();
        String intlPageURL = homeURL +"/"+ countrySettings + "/" + pageURL;
        logger.debug("getting url: " + intlPageURL);

        setUpEnvironment();

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

        setUpEnvironment();

        driver.get(envUrl);
    }

    private void getIntlHomePage(String country) {
        String envUrl = reader.getProperty("url");

        //Update Reader and create context
        Country context = new Country(envUrl, country);
        stateHolder.put("context", context);

        testData.updateReader();

        envUrl = context.getHomeurl();

        setUpEnvironment();

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

        setUpEnvironment();

        driver.get(envUrl);
    }
    
    private void setUpEnvironment() {
        setMonetate();
    	setCookie();
    }

    private void setMonetate() {
        if (reader.hasProperty("monetate")) {
            String monetateURL = testData.getData("monetate.url");
            driver.get(monetateURL);
        }
    }

    private void setCookie() {
        boolean setCookie = testData.getBoolean("setSidecarCookie");

        if(setCookie) {
            String url = reader.getProperty("url");
            String domain = url.replace("https://", "");
            driver.get(url + "/404");
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("document.cookie=\"x-origin=sidecar_render;path=/;domain=" + domain +
                    ";expires=new Date().setDate(new Date().getDate() + 1) \"");
            logger.info("Setting sidecar cookie as: {}", "document.cookie=\"x-origin=sidecar_render;path=/;domain=" +
                    domain + ";expires=new Date().setDate(new Date().getDate() + 1) \"");
        }
    }
    
    @Given("User navigates to ([^\"]*) with clean session")
    public void user_navigates_with_clean_session(String extensionUrl) {
        String country = reader.getProperty("country");

        switch (extensionUrl.toLowerCase().trim()) {
            case "category page":
                extensionUrl =  testData.getData("url.category");
                break;
            case "pdp":
                extensionUrl =  testData.getData("url.pdp");
                break;
            default:
                break;
        }

        String env = reader.getProperty("url");

        Country context = new Country(env, country);
        stateHolder.put("context", context);
        testData.updateReader();

        env = context.getHomeurl();

        driver.get(env + extensionUrl);
        Util.waitLoadingBar(driver);
    }
}
