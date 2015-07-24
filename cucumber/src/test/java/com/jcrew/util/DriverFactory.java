package com.jcrew.util;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DriverFactory {

    protected static WebDriver driver;
    private Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    public static final String[] PHANTOM_JS_ARGS = new String[]{"--web-security=false",
            "--ssl-protocol=any",
            "--local-to-remote-url-access=true",
            "--disk-cache=true",
            "--ignore-ssl-errors=true"
    };


    public DriverFactory() {
        initialize();
    }

    public void initialize() {
        try {
            if (driver == null) {
                createNewDriverInstance();
            }
        } catch (IOException ioe) {
            logger.warn("Configuration file not found, will use defaults", ioe);
        }
    }

    private void createNewDriverInstance() throws IOException {
        PropertyReader propertyReader = PropertyReader.getPropertyReader();
        String browser = propertyReader.getBrowser();

        if ("chrome".equals(browser)) {

            driver = new ChromeDriver();

        } else {

            logger.info("Using phantomjs driver");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setJavascriptEnabled(true);
            capabilities.setCapability("phantomjs.cli.args", PHANTOM_JS_ARGS);
            boolean isLocalEnvironment = propertyReader.isLocalEnvironment();
            if (!isLocalEnvironment) {
                capabilities.setCapability("phantomjs.binary.path", "./phantomjs");
            }
            capabilities.setCapability("phantomjs.page.settings.userAgent", propertyReader.getUserAgent());
            int width = propertyReader.getWindowWidth();
            int height = propertyReader.getWindowHeight();
            driver = new PhantomJSDriver(capabilities);
            driver.manage().window().setSize(new Dimension(width, height));
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void destroyDriver() {
        driver.quit();
        driver = null;
    }
}