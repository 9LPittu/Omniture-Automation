package com.jcrew.steps;

import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.UsersHub;
import com.jcrew.utils.Util;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class FinalSteps {

    private final Logger logger = LoggerFactory.getLogger(StartSteps.class);
    private DriverFactory driverFactory = new DriverFactory();
    private WebDriver driver = driverFactory.getDriver();
    private StateHolder holder = StateHolder.getInstance();

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (!scenario.isFailed()) {
            try {

                if (driver != null && "true".equalsIgnoreCase(System.getProperty("take.step.screenshot"))) {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.embed(screenshot, "image/png");
                }

            } catch (WebDriverException e) {
                logger.error("An exception happened when taking step screenshot after step", e);
                driverFactory.resetDriver();
            }
        }
    }

    @After
    public void quitDriver(Scenario scenario) throws IOException {

        if (scenario.isFailed()) {
            logger.debug("Taking screenshot of scenario {}", scenario.getName());
            try {
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
                
                String log = Util.logBrowserErrors(driver);
                scenario.embed(log.getBytes(), "text/plain");
                
                if(holder.hasKey("userObject")){
                	User user = holder.get("userObject");
                	String userName = "Email address: " + user.getEmail();
                	scenario.embed(userName.getBytes(), "text/plain");
                }
                
            } catch (WebDriverException e) {
                logger.error("An exception happened when taking step screenshot after scenario", e);
                driverFactory.resetDriver();
            }
        }

        driverFactory.destroyDriver();
        
        UsersHub userHub = UsersHub.getInstance();
        userHub.releaseUserCredentials();
        
        holder.clear();
    }
}