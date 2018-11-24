package com.jcrew.helper;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class BrowserFactory {

    
	private static final String BROWSER_PROP_KEY = "browser";
    public final static int DEFAULT_IMPLICIT_WAIT_TIME_IN_SECONDS = 30;

    /**
     * creates the browser driver specified in the system property "browser"
     * if no property is set then a firefox browser driver is created.
     * The allow properties are firefox, safari and chrome
     * e.g to run with safari, pass in the option -Dbrowser=safari at runtime
     *
     * @return WebDriver
     */
    public static WebDriver getBrowser() {
//        Browsers browser;
        WebDriver driver;
        //To change the Browser Type, change the 'browserType' in regression.properties
        // Supported Browsers are IE, chrome, firefox and the default is phantom.js
        driver = getBrowser(PropertyLoader.getBrowserType());
        driver.manage().deleteAllCookies();
        return driver;
    }

    public static WebDriver getBrowser(String browserType) {
        WebDriver driver = null;
        try {
            File file;
            DesiredCapabilities capabilities;
            switch (browserType) {
                case "chrome":
                    file = new File("Drivers" + File.separator + "chromedriver.exe");
                    //ChromeDriverManager.getInstance().setup();
                    System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
                    capabilities = DesiredCapabilities.chrome();
                    capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("disable-infobars");

                    String downloadFilepath = System.getProperty("user.dir") + File.separator + "temp";
                    File fileDownload = new File(downloadFilepath);
                    if (!fileDownload.exists()) {
                        fileDownload.mkdirs();
                    }
                    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                    chromePrefs.put("profile.default_content_settings.popups", 0);
                    chromePrefs.put("download.default_directory", downloadFilepath);
                    options.setExperimentalOption("prefs", chromePrefs);
                    driver = new ChromeDriver(options);
                    // driver = new ChromeDriver(capabilities);
                    break;
                case "IE":
                    file = new File("Drivers" + File.separator + "IEDriverServer.exe");
                    System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
                    capabilities = DesiredCapabilities.internetExplorer();
                    capabilities.setCapability("ignoreZoomSetting", true);
                    capabilities.setCapability("Enable Protected Mode", true);
                    driver = new InternetExplorerDriver(capabilities);
                    break;
                case "firefox":
                    file = new File("Drivers" + File.separator + "geckodriver.exe");
                    System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
                    capabilities = DesiredCapabilities.firefox();
                    capabilities.setCapability("marionette", true);
                    FirefoxProfile fp = getFirefoxProfile();
                    //fp.setEnableNativeEvents(true);
                    fp.setPreference("focusmanager.testmode", true);
                    capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
                    capabilities.setCapability(FirefoxDriver.PROFILE, fp);
                    driver = new FirefoxDriver(capabilities);
                    break;
                default:
                    capabilities = DesiredCapabilities.phantomjs();
                    capabilities.setJavascriptEnabled(true);
                    capabilities.setCapability("takesScreenshot", true);
                    capabilities.setCapability(
                            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                            PropertyLoader.getPhantomJsPath());
                    driver = new PhantomJSDriver(capabilities);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        addAllBrowserSetup(driver);
        driver.manage().timeouts().implicitlyWait(DEFAULT_IMPLICIT_WAIT_TIME_IN_SECONDS, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    
	private static WebDriver createSafariDriver() {
        return new SafariDriver();
    }

    private static WebDriver createChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        return new ChromeDriver();
    }

    private static WebDriver createFirefoxDriver(FirefoxProfile firefoxProfile) {
        return new FirefoxDriver(firefoxProfile);
    }

    private static FirefoxProfile getFirefoxProfile() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.dir", "/tmp");
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv");
        return profile;
    }

    private static void addAllBrowserSetup(WebDriver driver) {
        driver.manage().window().setPosition(new Point(0, 0));
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        org.openqa.selenium.Dimension dim = new org.openqa.selenium.Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
        driver.manage().window().setSize(dim);
    }

}

