package com.jcrew.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DriverFactory {

    private static final Map<String, WebDriver> driverMap = new HashMap<>();
    private final int DEFAULT_WINDOW_WIDTH = 400;
    private final int DEFAULT_WINDOW_HEIGHT = 667;
    private final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private final PropertyReader propertyReader = PropertyReader.getPropertyReader();
    private final StateHolder Holder = StateHolder.getInstance();


    private int width = DEFAULT_WINDOW_WIDTH;
    private int height = DEFAULT_WINDOW_HEIGHT;

    private WebDriver createNewDriverInstance() throws IOException {

        final WebDriver driver;
        final PropertyReader propertyReader = PropertyReader.getPropertyReader();

        if (propertyReader.isSystemPropertyTrue("remote.execution")) {
            driver = DriverGenarator.remoteDriver();
        } else {
            driver = DriverGenarator.localDriver();
        }

        setDimensions(driver);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        return driver;
    }

    private void setDimensions(WebDriver driver) {
        width = Integer.parseInt(propertyReader.getProperty("desktop.window.width"));
        height = Integer.parseInt(propertyReader.getProperty("desktop.window.height"));

        driver.manage().window().setSize(new Dimension(width, height));
    }

    public WebDriver getDriver() {
        String identifier = Thread.currentThread().getName();
        WebDriver driver = driverMap.get(identifier);

        if (driver == null) {
            try {
                driver = createNewDriverInstance();
                driverMap.put(identifier, driver);
            } catch (IOException e) {
                logger.error("unable to create driver: {}", e);
            }

        }

        return driver;
    }

    public void destroyDriver() {
        String identifier = Thread.currentThread().getName();
        WebDriver driver = driverMap.get(identifier);

        driver.quit();

        driverMap.remove(identifier);

    }

}