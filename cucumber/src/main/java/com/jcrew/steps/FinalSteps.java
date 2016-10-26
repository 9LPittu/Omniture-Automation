package com.jcrew.steps;

import com.jcrew.pojo.Product;
import com.jcrew.util.*;

import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinalSteps {

    private static final String TAKE_SCREENSHOT = "Screenshot";
    private final Logger logger = LoggerFactory.getLogger(FinalSteps.class);
    private final StateHolder stateHolder = StateHolder.getInstance();
    private DriverFactory driverFactory = new DriverFactory();
    private WebDriver driver = driverFactory.getDriver();

    @After
    public void quitDriver(Scenario scenario) {
    	 String data;

        if (driver != null && (scenario.isFailed() || scenario.getName().contains(TAKE_SCREENSHOT))) {
            logger.debug("Taking screenshot of scenario {}", scenario.getName());
            try {
                final byte[] screenshot = ScreenshotGenerator.getScreenshot(driver);
                scenario.embed(screenshot, "image/png");

                String log = Util.logBrowserErrors(driver);
                scenario.embed(log.getBytes(), "text/plain");

                data = getProducts();
                if (!data.isEmpty()) {
                    scenario.embed(data.getBytes(), "text/plain");
                }
                
                if(stateHolder.hasKey("sidecarusername")){
                	String userName = (String) stateHolder.get("sidecarusername");
                	scenario.embed(userName.getBytes(), "text/plain");
                }
                
                StartingSteps startSteps = new StartingSteps();
                try {
					startSteps.setupDriver();
				} catch (IOException e) {
					e.printStackTrace();
				}
                startSteps.deletes_browser_cookies();
                
            } catch (RuntimeException e) {
                logger.error("An exception happened when taking step screenshot after scenario", e);
                driverFactory.resetDriver();
            }
        }

        if (driverFactory != null && (boolean) stateHolder.get("deletecookies")) {
            driverFactory.destroyDriver();
        }

        PropertyReader reader = PropertyReader.getPropertyReader();
        if (!reader.getProperty("environment").equalsIgnoreCase("ci") && stateHolder.hasKey("sidecarusername")) {
            try {
                UsersHub userHub = UsersHub.getInstance();
                userHub.releaseUserCredentials();
            } catch (Exception e) {
                logger.error("Failed to release user '{}' in DB!!!", (String) stateHolder.get("sidecarusername"));
            }
        }

        stateHolder.clear();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (!scenario.isFailed()) {
            try {

                if (driver != null && "true".equalsIgnoreCase(System.getProperty("take.step.screenshot"))) {
                    byte[] screenshot = ScreenshotGenerator.getScreenshot(driver);
                    scenario.embed(screenshot, "image/png");
                }

            } catch (Exception e) {
                logger.error("An exception happened when taking step screenshot after step", e);
            }
        }
    }

    private String getProducts() {
        String products = "";

        if (stateHolder.hasKey("toBag")) {
            List<Product> inBag = stateHolder.getList("toBag");
            for (Product c : inBag) {
                products += c.getProductName() + "\t" +
                        c.getProductCode() + "\t" +
                        c.getSelectedColor() + "\t" +
                        c.getSelectedSize() + "\t" +
                        c.getPriceList() + "\n";
            }

            if (!products.isEmpty()) {
                products = "PRODUCT NAME\t\t\t\tITEM NUMBER\tCOLOR\tSIZE\tPRICE\n" + products;
            }
        }

        return products;
    }
}