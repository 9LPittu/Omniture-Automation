package com.jcrew.util;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

        if (propertyReader.hasProperty("window.width") && propertyReader.hasProperty("window.height")) {
            width = Integer.parseInt(propertyReader.getProperty("window.width"));
            height = Integer.parseInt(propertyReader.getProperty("window.height"));
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
        final WebDriver driver;

        if ("chrome".equals(browser)) {
            driver = new ChromeDriver();

        } else if ("firefox".equals(browser)) {

            driver = new FirefoxDriver();

        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setJavascriptEnabled(true);
            capabilities.setCapability("phantomjs.cli.args", PHANTOM_JS_ARGS);
            capabilities.setCapability("phantomjs.page.settings.userAgent", propertyReader.getProperty("user.agent"));

            driver = new PhantomJSDriver(capabilities);
        }

        driver.manage().window().setSize(new Dimension(width, height));
        return driver;
    }

    private WebDriver createRemoteDriver(PropertyReader propertyReader) throws MalformedURLException {
        final WebDriver driver;
        final String browser = propertyReader.getProperty("browser");

        if ("chrome".equals(browser)) {

            DesiredCapabilities chrome = DesiredCapabilities.chrome();
            chrome.setPlatform(Platform.WINDOWS);
            driver = getDesktopWebDriver(propertyReader, chrome);

        } else if ("firefox".equals(browser)) {

            DesiredCapabilities firefox = DesiredCapabilities.firefox();
            firefox.setPlatform(Platform.WINDOWS);
            driver = getDesktopWebDriver(propertyReader, firefox);

        } else if ("iossafari".equals(browser)) {

            DesiredCapabilities capabilities = DesiredCapabilities.iphone();

            capabilities.setCapability("browserName", "safari");
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", propertyReader.getProperty("device.name"));
            capabilities.setCapability("platformVersion", propertyReader.getProperty("device.os.version"));
            capabilities.setCapability("takesScreenshot", "true");
            capabilities.setCapability("acceptSslCerts", "true");
            capabilities.setCapability("autoAcceptAlerts", "true");

            if(propertyReader.hasProperty("device.udid")){
                //setting this capability is required only for iOS real device
                capabilities.setCapability("udid", propertyReader.getProperty("device.udid"));
            }

            capabilities.setCapability("bundleId", "com.bytearc.SafariLauncher");
            capabilities.setCapability("safariAllowPopups", true);
            capabilities.setCapability("safariOpenLinksInBackground", true);
            capabilities.setCapability("newCommandTimeout", 60);
            capabilities.setCapability("launchTimeout", 600000);

            driver = new RemoteWebDriver(getSeleniumRemoteAddress(propertyReader), capabilities);


        } else if ("androidchrome".equals(browser)) {
            DesiredCapabilities capabilities = DesiredCapabilities.android();
            capabilities.setPlatform(Platform.ANDROID);

            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, propertyReader.getProperty("device.name"));
            capabilities.setCapability(MobileCapabilityType.VERSION, propertyReader.getProperty("device.os.version"));
            capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
            capabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);
            capabilities.setCapability("autoAcceptAlerts", true);
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
            capabilities.setCapability("udid", propertyReader.getProperty("device.udid"));

            driver = new RemoteWebDriver(getSeleniumRemoteAddress(propertyReader), capabilities);

        } else {
            logger.debug(browser);
            final DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
            capabilities.setCapability("phantomjs.cli.args", PHANTOM_JS_ARGS);
            capabilities.setCapability("phantomjs.page.settings.userAgent", propertyReader.getProperty("user.agent"));

            driver = getDesktopWebDriver(propertyReader, capabilities);
        }

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        return driver;
    }

    private URL getSeleniumRemoteAddress(PropertyReader propertyReader) throws MalformedURLException {
        final String seleniumHubUrl = propertyReader.getProperty("selenium.grid.hub.url");
        return new URL(seleniumHubUrl);
    }

    private WebDriver getDesktopWebDriver(PropertyReader propertyReader, DesiredCapabilities desiredCapabilities) throws MalformedURLException {
        final URL seleniumHubRemoteAddress = getSeleniumRemoteAddress(propertyReader);
        final WebDriver driver = new RemoteWebDriver(seleniumHubRemoteAddress, desiredCapabilities);

        driver.manage().window().setSize(new Dimension(width, height));
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

        if ("iossafari".equals(propertyReader.getProperty("browser"))) {
            for (Cookie cookie : driver.manage().getCookies()) {
                driver.manage().deleteCookie(cookie);
            }
        }
        if ("androidchrome".equals(propertyReader.getProperty("browser"))) {
            for (Cookie cookie : driver.manage().getCookies()) {
                if (!((cookie.getName()).equalsIgnoreCase("SESSIONID"))) {
                    driver.manage().deleteCookie(cookie);
                }
            }
        }

        if (driver != null && !"iossafari".equals(propertyReader.getProperty("browser"))) {
            driver.quit();
            driverMap.remove(identifier);
        }
         }
    }