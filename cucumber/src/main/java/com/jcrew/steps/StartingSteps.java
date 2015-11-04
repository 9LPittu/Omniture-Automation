package com.jcrew.steps;

import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import cucumber.api.java.BeforeStep;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class StartingSteps {

    private static final String TAKE_SCREENSHOT = "Screenshot";
    private final Logger logger = LoggerFactory.getLogger(StartingSteps.class);
    private final PropertyReader reader = PropertyReader.getPropertyReader();
    private final StateHolder stateHolder = StateHolder.getInstance();
    private DriverFactory driverFactory;
    private WebDriver driver;

    @Before
    public void setupDriver() throws IOException {
        driverFactory = new DriverFactory();
        driver = driverFactory.getDriver();
    }

    @Given("^User is on homepage$")
    public void user_is_on_home_page() throws Throwable {
        int retry = 0;
        boolean successfulLoad = false;
        while (retry < 5 && !successfulLoad) {
            try {
                driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                driver.get(reader.getEnvironment());
                Util.waitForPageFullyLoaded(driver);
                successfulLoad = true;
            } catch (TimeoutException te) {
                logger.debug("Page did not load retry: {}", retry + 1);
                retry++;
            }
        }

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

        stateHolder.clear();
    }

    @BeforeStep
    public void beforeStep(Scenario s) {
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (!scenario.isFailed()) {
            try {

                if (driver != null && "true".equalsIgnoreCase(System.getProperty("take.step.screenshot"))) {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.embed(screenshot, "image/png");
                }

            } catch (Exception e) {
                logger.error("An exception happened when taking step screenshot", e);
            }
        }
    }
}
