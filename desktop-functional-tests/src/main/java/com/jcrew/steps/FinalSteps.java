package com.jcrew.steps;

import com.jcrew.pojo.User;
import com.jcrew.pojo.Product;
import com.jcrew.pojo.User;

import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.UsersHub;
import com.jcrew.utils.Util;
import com.jcrew.utils.ScreenshotGenerator;

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
import java.util.List;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class FinalSteps {

    private final Logger logger = LoggerFactory.getLogger(StartSteps.class);
    private DriverFactory driverFactory = new DriverFactory();
    private WebDriver driver = driverFactory.getDriver();
    private StateHolder holder = StateHolder.getInstance();
    private final StateHolder stateHolder = StateHolder.getInstance();

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (!scenario.isFailed()) {
            try {

                if (driver != null && "true".equalsIgnoreCase(System.getProperty("take.step.screenshot"))) {
                	byte[] screenshot = ScreenshotGenerator.getScreenshot(driver);
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
    	String data;

        if (scenario.isFailed()) {
            logger.debug("Taking screenshot of scenario {}", scenario.getName());
            try {
            	final byte[] screenshot = ScreenshotGenerator.getScreenshot(driver);
                scenario.embed(screenshot, "image/png");
                
                String log = Util.logBrowserErrors(driver);
                scenario.embed(log.getBytes(), "text/plain");
                
                data = getExecutionDetails();
                if (!data.isEmpty()) {
                    scenario.embed(data.getBytes(), "text/plain");
                }
                
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
    
    private String getExecutionDetails() {
        String products = "";
        String userDetails = "";

        if (stateHolder.hasKey("toBag")) {
            List<Product> inBag = stateHolder.getList("toBag");
            for (Product c : inBag) {
                products += c.getProductName() + "\t" +
                        c.getItemNumber() + "\t" +
                        c.getSelectedColor() + "\t" +
                        c.getSelectedSize() + "\t" +
                        c.getPriceList() + "\n";
            }

            if (!products.isEmpty()) {
                products = "PRODUCT NAME\t\t\t\tITEM NUMBER\tCOLOR\tSIZE\tPRICE\n" + products + "\n";
            }
        }
        
        if (stateHolder.hasKey("userObject")) {
            User user = stateHolder.get("userObject");
            userDetails = user.getEmail() + "\t" + user.getFirstName() + "\t" + user.getLastName() + "\n";
            userDetails = "User Name\t\t\tFirst Name\tLast Name\n" + userDetails + "\n";
        }
        return products + userDetails;
    }
}