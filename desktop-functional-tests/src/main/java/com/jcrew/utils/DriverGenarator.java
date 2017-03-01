package com.jcrew.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ngarcia on 3/1/17.
 */
public class DriverGenarator {

    private static PropertyReader propertyReader = PropertyReader.getPropertyReader();

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

        return new RemoteWebDriver(new URL(propertyReader.getProperty("selenium.grid.hub.url")), capabilities);
    }

    public static WebDriver localDriver() throws MalformedURLException {
        DesiredCapabilities capabilities;
        WebDriver driver;

        switch (propertyReader.getProperty("browser")) {
            case "chrome":
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
