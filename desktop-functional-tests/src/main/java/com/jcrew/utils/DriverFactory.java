package com.jcrew.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {


    private static final String[] PHANTOM_JS_ARGS = new String[]{"--web-security=false",
            "--ssl-protocol=any",
            "--local-to-remote-url-access=true",
            "--disk-cache=false",
            "--ignore-ssl-errors=true"
    };
    private static final Map<String, WebDriver> driverMap = new HashMap<>();
    private final int DEFAULT_WINDOW_WIDTH = 400;
    private final int DEFAULT_WINDOW_HEIGHT = 667;
    private final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    private int width = DEFAULT_WINDOW_WIDTH;
    private int height = DEFAULT_WINDOW_HEIGHT;

    private WebDriver createNewDriverInstance() throws IOException {

        final WebDriver driver;
        final PropertyReader propertyReader = PropertyReader.getPropertyReader();
        final String browser = propertyReader.getProperty("browser");


        if ("device".equals(browser)) {

            width = Integer.parseInt(propertyReader.getProperty("device.window.width"));
            height = Integer.parseInt(propertyReader.getProperty("device.window.height"));
        }
        else {
            width = Integer.parseInt(propertyReader.getProperty("desktop.window.width"));
            height = Integer.parseInt(propertyReader.getProperty("desktop.window.height"));
        }

        if (propertyReader.isSystemPropertyTrue("remote.execution")) {
            driver = createRemoteDriver(propertyReader);
        } else {
            driver = createLocalDriver(propertyReader);
        }

        return driver;
    }

    private WebDriver createLocalDriver(PropertyReader propertyReader) {
        final String browser = propertyReader.getProperty("browser");
        WebDriver driver = null;

        if ("chrome".equals(browser)) {
            driver = new ChromeDriver();
            driver.manage().window().setSize(new Dimension(width, height));

        } else if ("firefox".equals(browser)) {
            driver = new FirefoxDriver();
            driver.manage().window().setSize(new Dimension(width, height));

        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setJavascriptEnabled(true);
            capabilities.setCapability("phantomjs.cli.args", PHANTOM_JS_ARGS);
            capabilities.setCapability("phantomjs.page.settings.userAgent", propertyReader.getProperty("user.agent"));

            driver = new PhantomJSDriver(capabilities);
            driver.manage().window().setSize(new Dimension(width, height));
        }

        return driver;
    }

    private WebDriver createRemoteDriver(PropertyReader propertyReader) throws MalformedURLException {
        final String browser = propertyReader.getProperty("browser");
        final String gridURL = propertyReader.getProperty("selenium.grid.hub.url");
        final String nodeURL = propertyReader.getProperty("selenium.grid.node.url");
        DesiredCapabilities desiredCapabilities;
        WebDriver driver = null;


        if ("chrome".equals(browser)) {
            desiredCapabilities = DesiredCapabilities.chrome();
            driver = new RemoteWebDriver(new URL(gridURL), desiredCapabilities);
            driver.manage().window().setSize(new Dimension(width, height));

        } else if ("firefox".equals(browser)) {
            desiredCapabilities = DesiredCapabilities.firefox();
            driver = new RemoteWebDriver(new URL(gridURL), desiredCapabilities);
            driver.manage().window().setSize(new Dimension(width, height));

        } else if (("ipad".equals(browser))||("ipad7.1".equals(browser))) {

            String ipadName = propertyReader.getProperty(browser+".name");
            String ipadOs = propertyReader.getProperty(browser+".os.version");
            String ipadUdid = propertyReader.getProperty(browser+".udid");
            desiredCapabilities = DesiredCapabilities.ipad();
            desiredCapabilities.setCapability("browserName", "safari");
            desiredCapabilities.setCapability("platformName", "iOS");
            desiredCapabilities.setCapability("deviceName", ipadName);
            desiredCapabilities.setCapability("platformVersion", ipadOs);
            desiredCapabilities.setCapability("takesScreenshot", "true");
            desiredCapabilities.setCapability("acceptSslCerts", "true");
            desiredCapabilities.setCapability("autoAcceptAlerts", "true");
            desiredCapabilities.setCapability("udid", ipadUdid);
            desiredCapabilities.setCapability("bundleId", "com.bytearc.SafariLauncher");
            desiredCapabilities.setCapability("safariAllowPopups", true);
            desiredCapabilities.setCapability("safariOpenLinksInBackground", true);
            desiredCapabilities.setCapability("newCommandTimeout", 240);
            desiredCapabilities.setCapability("launchTimeout", 600000);

            driver = new RemoteWebDriver(new URL(nodeURL), desiredCapabilities);

       } else {
            desiredCapabilities = DesiredCapabilities.phantomjs();
            desiredCapabilities.setCapability("phantomjs.cli.args", PHANTOM_JS_ARGS);
            desiredCapabilities.setCapability("phantomjs.page.settings.userAgent", propertyReader.getProperty(browser + ".user.agent"));
            driver = new RemoteWebDriver(new URL(gridURL), desiredCapabilities);
            driver.manage().window().setSize(new Dimension(width, height));
        }

        return driver;
    }

    public WebDriver getDriver() {
        String identifier = Thread.currentThread().getName();
        WebDriver driver = driverMap.get(identifier);

        if (driver == null) {
            try {
                driver = createNewDriverInstance();
                driverMap.put(identifier, driver);
            } catch (IOException e) {
                logger.error("unable to create driver");
            }

        }

        return driver;
    }

    public void destroyDriver() {
        String identifier = Thread.currentThread().getName();
        WebDriver driver = driverMap.get(identifier);
        PropertyReader propertyReader = PropertyReader.getPropertyReader();

        if (driver != null && !"iossafari".equals(propertyReader.getProperty("browser"))) {
            driver.quit();
            driverMap.remove(identifier);
        }
    }

}