package com.jcrew.utils;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ngarcia on 3/1/17.
 */
public class DriverGenerator {

    private static PropertyReader propertyReader = PropertyReader.getPropertyReader();
    private static final Logger logger = LoggerFactory.getLogger(DriverGenerator.class);

    public static WebDriver remoteDriver() throws MalformedURLException {
        String browser = propertyReader.getProperty("browser");

        DesiredCapabilities capabilities;

        switch (browser) {
            case "chrome":
                capabilities = getChromeCapabilities();
                break;
            case "firefox":
                capabilities = getFireFoxCapabilites();
                break;
            default:
                capabilities = getPhantomJSCapabilities();
                break;
        }

        int tries = 4;

        while (tries > 0) {
            try {

                return new RemoteWebDriver(new URL(propertyReader.getProperty("selenium.grid.hub.url")), capabilities);

            } catch (SessionNotCreatedException notCreated) {
                logger.error("Session not created, attempting again");
                tries--;
            }
        }

        return null;
    }

    public static WebDriver localDriver() throws MalformedURLException {
        DesiredCapabilities capabilities;
        WebDriver driver;

        switch (propertyReader.getProperty("browser")) {
            case "chrome":
            	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Softwares\\chromedriver.exe");
                capabilities = getChromeCapabilities();
                driver = new ChromeDriver(capabilities);
                break;
            case "firefox":
                capabilities = getFireFoxCapabilites();
                driver = new FirefoxDriver(capabilities);
                break;
            default:
                capabilities = getPhantomJSCapabilities();
                driver = new PhantomJSDriver(capabilities);
                break;
        }

        return driver;
    }

    private static DesiredCapabilities getChromeCapabilities() {
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        options.addArguments("disable-infobars");

        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);

        return desiredCapabilities;
    }

    private static DesiredCapabilities getFireFoxCapabilites() {
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();

        FirefoxProfile profile = new FirefoxProfile();
        desiredCapabilities.setCapability(FirefoxDriver.PROFILE, profile);

        return desiredCapabilities;
    }

    private static DesiredCapabilities getPhantomJSCapabilities() {
        String[] PHANTOM_JS_ARGS = new String[]{"--web-security=false",
                "--ssl-protocol=any",
                "--local-to-remote-url-access=true",
                "--disk-cache=false",
                "--ignore-ssl-errors=true"
        };

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
        desiredCapabilities.setCapability("phantomjs.cli.args", PHANTOM_JS_ARGS);
        desiredCapabilities.setCapability("phantomjs.page.settings.userAgent",
                propertyReader.getProperty("user.agent"));

        return desiredCapabilities;
    }
}
