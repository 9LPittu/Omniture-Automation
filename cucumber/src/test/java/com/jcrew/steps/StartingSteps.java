package com.jcrew.steps;

import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.Reporting;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class StartingSteps {

    private static final String TAKE_SCREENSHOT = "Screenshot";
    private final Logger logger = LoggerFactory.getLogger(StartingSteps.class);
    private DriverFactory driverFactory;
    private WebDriver driver;
    private final PropertyReader reader = PropertyReader.getPropertyReader();
    private Scenario scenario;
    private Reporting reporting = new Reporting();

    @Before
    public void setupDriver() throws IOException {
        driverFactory = new DriverFactory();
        driver = driverFactory.getDriver();
    }
    
    @Before
    public void getScenarioObject(Scenario s){
    	this.scenario = s;    	
    }

    @Given("^User is on homepage$")
    public void user_is_on_home_page() throws Throwable {    	
    	
        int retry = 0;
        boolean successfulLoad = false;
        while (retry < 5 && !successfulLoad) {
            try {
	                driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	                
	                //maximize the browser when executing on desktop browsers
	                final String browser = reader.getBrowser();
	                if(browser.equalsIgnoreCase("firefox") || browser.equalsIgnoreCase("chrome") || browser.equalsIgnoreCase("safari") || browser.equalsIgnoreCase("IE") || browser.equalsIgnoreCase("internetexplorer")){
	                	driver.manage().window().maximize();
	                }
	                
	                driver.get(reader.getEnvironment());
	                waitForPageToLoadUpToTheLastElementPriorScriptExecution();
	                successfulLoad = true;
            } catch (TimeoutException te) {
	                logger.debug("Page did not load retry: {}", retry + 1 );
	                retry++;
            }
        }
        
        reporting.takeScreenshot(scenario);
    }

    private void waitForPageToLoadUpToTheLastElementPriorScriptExecution() {
        new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOfElementLocated(
                By.id("jchp-module35")));
    }

    @And("^User goes to homepage$")
    public void user_goes_to_homepage() throws Throwable {
        driver.get(reader.getEnvironment());
    }

    @After
    public void quitDriver(Scenario scenario) throws IOException {

        if (driver != null && (scenario.isFailed() || scenario.getName().contains(TAKE_SCREENSHOT))) {

            logger.debug("Taking screenshot of scenario {}", scenario.getName());

            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        }

        if (driverFactory != null) {
            driverFactory.destroyDriver();
        }
    }

}
