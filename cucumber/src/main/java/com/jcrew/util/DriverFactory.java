package com.jcrew.util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
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
    private final StateHolder stateHolder = StateHolder.getInstance();
    private final PropertyReader propertyReader = PropertyReader.getPropertyReader();

    private int width = DEFAULT_WINDOW_WIDTH;
    private int height = DEFAULT_WINDOW_HEIGHT;

    private WebDriver createNewDriverInstance() throws IOException {

        final WebDriver driver;

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
        final String viewport = propertyReader.getProperty("viewport");
        final String browser = propertyReader.getProperty("browser");
        final boolean isDesktop = propertyReader.isSystemPropertyTrue("is.desktop");
        boolean akamaiEnv = Boolean.parseBoolean(
                propertyReader.getProperty(propertyReader.getProperty("environment") + ".akamai"));

        WebDriver driver = null;

        if ("chrome".equals(browser)) {
        	DesiredCapabilities desiredCapabilities = getChromeCapabilities(akamaiEnv);
            driver = new ChromeDriver(desiredCapabilities);
        	
        	if (!isDesktop)
        		driver.manage().window().setSize(new Dimension(width, height));
        	
            if (!akamaiEnv && !isDesktop) {
                driver.get("chrome-extension://idgpnmonknjnojddfkpgkljpfnnfcklj/icon.png");
                ((JavascriptExecutor) driver).executeScript("localStorage.setItem('profiles', JSON.stringify([{" +
                        "'title':'Profile 1'," +
                        "'hideComment':true," +
                        "'headers':[{'enabled':true," +
                        "'name':'X-Akamai-Mobile'," +
                        "'value':'true'," +
                        "'comment':''}]," +
                        "'respHeaders':[]," +
                        "'filters':[]," +
                        "'appendMode':''}]));");
            }

        } else if ("firefox".equals(browser)) {
            driver = new FirefoxDriver(getFirefoxProfile());
            if (!isDesktop)
                driver.manage().window().setSize(new Dimension(width, height));

        } else if ("iossafari".equals(browser)) {

            DesiredCapabilities capabilities = DesiredCapabilities.iphone();

            capabilities.setCapability("browserName", "safari");
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", propertyReader.getProperty(viewport + ".device.name"));
            capabilities.setCapability("platformVersion", propertyReader.getProperty(viewport + ".device.os.version"));
            capabilities.setCapability("takesScreenshot", "true");
            capabilities.setCapability("acceptSslCerts", "true");
            capabilities.setCapability("autoAcceptAlerts", "true");

            if (propertyReader.hasProperty(viewport + ".device.udid")) {
                //setting this capability is required only for iOS real device
                capabilities.setCapability("udid", propertyReader.getProperty(viewport + ".device.udid"));
            }

            capabilities.setCapability("safariAllowPopups", true);
            capabilities.setCapability("safariOpenLinksInBackground", true);
            capabilities.setCapability("newCommandTimeout", 180);
            capabilities.setCapability("launchTimeout", 600000);

            try {
                driver = new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
            } catch (MalformedURLException exception) {
                logger.error("NOT ABLE TO CREATE IOS DRIVER");
            }

        } else if ("androidchrome".equals(browser)) {
            DesiredCapabilities capabilities = DesiredCapabilities.android();
            capabilities.setPlatform(Platform.ANDROID);

            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, propertyReader.getProperty(viewport + ".device.name"));
            capabilities.setCapability(MobileCapabilityType.VERSION, propertyReader.getProperty(viewport + ".device.os.version"));
            capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
            capabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);
            capabilities.setCapability("autoAcceptAlerts", true);
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
            capabilities.setCapability("udid", propertyReader.getProperty(viewport + ".device.udid"));
            capabilities.setCapability("newCommandTimeout", 180);

            try {
                driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
            } catch (MalformedURLException exception) {
                logger.error("NOT ABLE TO CREATE IOS DRIVER");
            }

        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setJavascriptEnabled(true);
            capabilities.setCapability("phantomjs.cli.args", PHANTOM_JS_ARGS);
            if (!isDesktop)
                capabilities.setCapability("phantomjs.page.settings.userAgent", propertyReader.getProperty("user.agent"));

            if (!akamaiEnv && !isDesktop) {
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX +
                        "X-Akamai-Mobile", "true");
            }

            driver = new PhantomJSDriver(capabilities);
            if (!isDesktop)
                driver.manage().window().setSize(new Dimension(width, height));
        }

        return driver;
    }

    private WebDriver createRemoteDriver(PropertyReader propertyReader) throws MalformedURLException {
        final WebDriver driver;
        final String viewport = propertyReader.getProperty("viewport");
        final String browser = propertyReader.getProperty("browser");
        final boolean isDesktop = propertyReader.isSystemPropertyTrue("is.desktop");
        boolean akamaiEnv = Boolean.parseBoolean(
                propertyReader.getProperty(propertyReader.getProperty("environment") + ".akamai"));

        if ("chrome".equals(browser)) {
        	DesiredCapabilities capabilities = getChromeCapabilities(akamaiEnv);
            driver = getDesktopWebDriver(propertyReader, capabilities);

            if (!akamaiEnv && !isDesktop) {
                driver.get("chrome-extension://idgpnmonknjnojddfkpgkljpfnnfcklj/icon.png");
                ((JavascriptExecutor) driver).executeScript("localStorage.setItem('profiles', JSON.stringify([{" +
                        "'title':'Profile 1'," +
                        "'hideComment':true," +
                        "'headers':[{'enabled':true," +
                        "'name':'X-Akamai-Mobile'," +
                        "'value':'true'," +
                        "'comment':''}]," +
                        "'respHeaders':[]," +
                        "'filters':[]," +
                        "'appendMode':''}]));");

            }

        } else if ("firefox".equals(browser)) {
            DesiredCapabilities firefox = DesiredCapabilities.firefox();
            firefox.setCapability(FirefoxDriver.PROFILE, getFirefoxProfile());
            firefox.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            driver = getDesktopWebDriver(propertyReader, firefox);

        } else if ("iossafari".equals(browser)) {

            DesiredCapabilities capabilities = DesiredCapabilities.iphone();

            capabilities.setCapability("browserName", "safari");
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", propertyReader.getProperty(viewport + ".device.name"));
            capabilities.setCapability("platformVersion", propertyReader.getProperty(viewport + ".device.os.version"));
            capabilities.setCapability("takesScreenshot", "true");
            capabilities.setCapability("acceptSslCerts", "true");
            capabilities.setCapability("autoAcceptAlerts", "true");

            if (propertyReader.hasProperty(viewport + ".device.udid")) {
                //setting this capability is required only for iOS real device
                capabilities.setCapability("udid", propertyReader.getProperty(viewport + ".device.udid"));
            }

            capabilities.setCapability("bundleId", "com.bytearc.SafariLauncher");
            capabilities.setCapability("safariAllowPopups", true);
            capabilities.setCapability("safariOpenLinksInBackground", true);
            capabilities.setCapability("newCommandTimeout", 180);
            capabilities.setCapability("launchTimeout", 600000);

            driver = new RemoteWebDriver(getSeleniumRemoteAddress(propertyReader), capabilities);

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        } else if ("androidchrome".equals(browser)) {
            DesiredCapabilities capabilities = DesiredCapabilities.android();
            capabilities.setPlatform(Platform.ANDROID);

            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, propertyReader.getProperty(viewport + ".device.name"));
            capabilities.setCapability(MobileCapabilityType.VERSION, propertyReader.getProperty(viewport + ".device.os.version"));
            capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
            capabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);
            capabilities.setCapability("autoAcceptAlerts", true);
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
            capabilities.setCapability("udid", propertyReader.getProperty(viewport + ".device.udid"));
            capabilities.setCapability("newCommandTimeout", 180);

            driver = new RemoteWebDriver(getSeleniumRemoteAddress(propertyReader), capabilities);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        } else {
            logger.debug(browser);
            final DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
            capabilities.setCapability("phantomjs.cli.args", PHANTOM_JS_ARGS);
            if (!isDesktop)
                capabilities.setCapability("phantomjs.page.settings.userAgent", propertyReader.getProperty("user.agent"));

            if (!akamaiEnv && !isDesktop) {
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX +
                        "X-Akamai-Mobile", "true");
            }

            driver = getDesktopWebDriver(propertyReader, capabilities);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }

        return driver;
    }

    private URL getSeleniumRemoteAddress(PropertyReader propertyReader) throws MalformedURLException {
        final String seleniumHubUrl = propertyReader.getProperty("selenium.grid.hub.url");
        return new URL(seleniumHubUrl);
    }

    private WebDriver getDesktopWebDriver(PropertyReader propertyReader, DesiredCapabilities desiredCapabilities) throws MalformedURLException {
        final URL seleniumHubRemoteAddress = getSeleniumRemoteAddress(propertyReader);
        final WebDriver driver = new RemoteWebDriver(seleniumHubRemoteAddress, desiredCapabilities);
        final boolean isDesktop = propertyReader.isSystemPropertyTrue("is.desktop");

        if (!isDesktop)
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

    public FirefoxProfile getFirefoxProfile() {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("general.useragent.override", propertyReader.getProperty("user.agent"));
        return firefoxProfile;
    }

    public void destroyDriver() {
        String identifier = Thread.currentThread().getName();
        WebDriver driver = driverMap.get(identifier);

        if (driver != null && !"iossafari".equals(propertyReader.getProperty("browser"))) {
            driver.quit();
            driverMap.remove(identifier);
        }
    }

    public void deleteBrowserCookies() {
        stateHolder.put("deletecookies", true);
        String identifier = Thread.currentThread().getName();
        WebDriver driver = driverMap.get(identifier);

        Set<Cookie> cookies = null;
        try {
            cookies = driver.manage().getCookies();
            String browser = propertyReader.getProperty("browser");
            if (!cookies.isEmpty()) {
                if ("iossafari".equals(browser)) {
                    for (Cookie cookie : cookies) {
                        if (!((cookie.getName()).equalsIgnoreCase("is_sidecar")) && !((cookie.getName()).equalsIgnoreCase("SESSIONID"))) {
                            driver.manage().deleteCookie(cookie);
                        }
                    }

                } else if ("androidchrome".equals(browser) || "phantomjs".equals(browser) || "firefox".equals(browser) || "chrome".equals(browser)) {
                    for (Cookie cookie : cookies) {
                        if (!((cookie.getName()).equalsIgnoreCase("SESSIONID"))) {
                            driver.manage().deleteCookie(cookie);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Not able to delete cookies", e);
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
    
    private DesiredCapabilities getChromeCapabilities(boolean isAkamai) {
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();

        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", width);
        deviceMetrics.put("height", height);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", propertyReader.getProperty("user.agent"));

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("mobileEmulation", mobileEmulation);

        if(isAkamai) {
            options.addArguments("--disable-extensions");

        } else {
            options.addExtensions(new File("ModHeader.crx"));

        }

        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);

        return desiredCapabilities;
    }
}
