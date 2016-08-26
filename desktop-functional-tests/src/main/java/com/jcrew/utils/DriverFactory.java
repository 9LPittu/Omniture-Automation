package com.jcrew.utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DriverFactory {


    private static final String[] PHANTOM_JS_ARGS = new String[]{
            "--web-security=false",
            "--ssl-protocol=any",
            "--local-to-remote-url-access=true",
            "--disk-cache=false",
            "--ignore-ssl-errors=true"
    };
    private static final Map<String, WebDriver> driverMap = new HashMap<>();
    private final int DEFAULT_WINDOW_WIDTH = 400;
    private final int DEFAULT_WINDOW_HEIGHT = 667;
    private final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private final StateHolder Holder = StateHolder.getInstance();


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

    private WebDriver createLocalDriver(PropertyReader propertyReader) throws MalformedURLException {
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
            capabilities.setCapability("phantomjs.page.settings.userAgent", propertyReader.getProperty("desktop.user.agent"));

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

        } else if (("ipad".equals(browser))||("ipad9.3".equals(browser))) {

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

       } else if ("tablet".equals(browser)) {

            String tabletName = propertyReader.getProperty(browser+".name");
            String tabletOs = propertyReader.getProperty(browser+".os.version");
            String tabletUdid = propertyReader.getProperty(browser+".udid");

            desiredCapabilities = DesiredCapabilities.android();
            desiredCapabilities.setPlatform(Platform.ANDROID);

            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, tabletName);
            desiredCapabilities.setCapability(MobileCapabilityType.VERSION, tabletOs);
            desiredCapabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
            desiredCapabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);
            desiredCapabilities.setCapability("autoAcceptAlerts", true);
            desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
            desiredCapabilities.setCapability("udid", tabletUdid);
            desiredCapabilities.setCapability("newCommandTimeout", 240);

            driver = new RemoteWebDriver(new URL(gridURL), desiredCapabilities);

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
                logger.error("unable to create driver: {}", e);
            }

        }

        return driver;
    }

    public void destroyDriver() {
        String identifier = Thread.currentThread().getName();
        WebDriver driver = driverMap.get(identifier);
        PropertyReader propertyReader = PropertyReader.getPropertyReader();

        if (driver != null && !"iossafari".equals(propertyReader.getProperty("browser"))) {
        	deleteBrowserCookies();
            driver.quit();
            driverMap.remove(identifier);
        }
    }

    public void resetDriver() {
        String identifier = Thread.currentThread().getName();
        driverMap.remove(identifier);

        try {
            WebDriver driver = createNewDriverInstance();
            deleteBrowserCookies();
            driverMap.put(identifier, driver);
        } catch (IOException e) {
            logger.error("unable to create driver in a reset");
        }
    }

    public void deleteBrowserCookies(){
        Holder.put("deletecookies", true);
        String identifier = Thread.currentThread().getName();
        WebDriver driver = driverMap.get(identifier);
        PropertyReader propertyReader = PropertyReader.getPropertyReader();

        Set<Cookie> cookies = null;
        try{
            cookies = driver.manage().getCookies();
            String browser = propertyReader.getProperty("browser");
            if(!cookies.isEmpty()) {
                if ("iossafari".equals(browser)) {
                    for (Cookie cookie : cookies) {
                        if (!((cookie.getName()).equalsIgnoreCase("is_sidecar")) && !((cookie.getName()).equalsIgnoreCase("SESSIONID"))) {
                            driver.manage().deleteCookie(cookie);
                        }
                    }

                } else if ("androidchrome".equals(browser) || "device".equals(browser) ) {
                    for (Cookie cookie : cookies) {
                        if (!((cookie.getName()).equalsIgnoreCase("SESSIONID"))) {
                            driver.manage().deleteCookie(cookie);
                        }
                    }
                }
                else if("desktop".equals(browser)){
                	for (Cookie cookie : cookies) {
                		driver.manage().deleteCookie(cookie);
                	}
                }
            }
        } catch (Exception e){
            logger.error("Not able to delete cookies", e);
        }

    }

}