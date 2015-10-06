package com.jcrew.util;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DriverFactory {

    private static Map<String, WebDriver> driverMap = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    public static final String[] PHANTOM_JS_ARGS = new String[]{"--web-security=false",
            "--ssl-protocol=any",
            "--local-to-remote-url-access=true",
            "--disk-cache=true",
            "--ignore-ssl-errors=true"
    };

    private WebDriver createNewDriverInstance() throws IOException {

        final WebDriver driver;
        final PropertyReader propertyReader = PropertyReader.getPropertyReader();
        if (propertyReader.isRemoteExecution()) {
            driver = createRemoteDriver(propertyReader);
        } else {
            driver = createLocalDriver(propertyReader);
        }

        return driver;
    }

    private WebDriver createLocalDriver(PropertyReader propertyReader) {
        final String browser = propertyReader.getBrowser();
        final WebDriver driver;
        if ("chrome".equals(browser)) {

            driver = new ChromeDriver();

        } else if ("firefox".equals(browser)) {

            driver = new FirefoxDriver();
        
        } else if("androidchrome".equals(browser) || "androidbrowser".equals(browser) || "iossafari".equals(browser)){
        	driver = getMobileDriver(propertyReader); 
        	
        } else {

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setJavascriptEnabled(true);
            capabilities.setCapability("phantomjs.cli.args", PHANTOM_JS_ARGS);
            capabilities.setCapability("phantomjs.page.settings.userAgent", propertyReader.getUserAgent());

            driver = new PhantomJSDriver(capabilities);

        }

        int width = propertyReader.getWindowWidth();
        int height = propertyReader.getWindowHeight();
        driver.manage().window().setSize(new Dimension(width, height));

        return driver;

    }

    private WebDriver createRemoteDriver(PropertyReader propertyReader) throws MalformedURLException {
        final WebDriver driver;
        final String browser = propertyReader.getBrowser();
        final String seleniumHubUrl = propertyReader.getSeleniumHubUrl();
        final URL seleniumHubRemoteAddress = new URL(seleniumHubUrl);

        if ("chrome".equals(browser)) {

            driver = new RemoteWebDriver(seleniumHubRemoteAddress, DesiredCapabilities.chrome());

        } else if ("firefox".equals(browser)) {

            driver = new RemoteWebDriver(seleniumHubRemoteAddress, DesiredCapabilities.firefox());
        
        } else if("androidchrome".equals(browser) || "androidbrowser".equals(browser) || "iossafari".equals(browser)){
        	driver = getMobileDriver(propertyReader); 
        	
        } else {

            final DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
            capabilities.setCapability("phantomjs.cli.args", PHANTOM_JS_ARGS);
            capabilities.setCapability("phantomjs.page.settings.userAgent", propertyReader.getUserAgent());

            driver = new RemoteWebDriver(seleniumHubRemoteAddress, capabilities);

        }

        int width = propertyReader.getWindowWidth();
        int height = propertyReader.getWindowHeight();
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
    
    public WebDriver getMobileDriver(PropertyReader propertyReader){
    	
   	 final String browser = propertyReader.getBrowser();
   	 final String seleniumHubUrl = propertyReader.getSeleniumHubUrl();
     String identifier = Thread.currentThread().getName();
     WebDriver driver = driverMap.get(identifier);
     DesiredCapabilities capabilities;
   	
   	try{    	    
   	    switch(browser.toLowerCase()){
   	    
   		   case "androidchrome":
   			   
   			   capabilities = DesiredCapabilities.android();
  				
   			   capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,MobilePlatform.ANDROID);            				
   			   capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,propertyReader.getProperty("device.name"));
   			   capabilities.setCapability(MobileCapabilityType.VERSION, propertyReader.getProperty("device.os.version"));
   			   capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
   			   capabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);
   			   capabilities.setCapability("autoAcceptAlerts", true);
   			   capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");   			   
   			   
   			   driver = new RemoteWebDriver(new URL(seleniumHubUrl),capabilities);
  			   driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  			   driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
      			   
  			   break;
   		   
   		   case "androidbrowser":
   			   
   			   capabilities = DesiredCapabilities.android();
  				
   			   capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,MobilePlatform.ANDROID);            				
   			   capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,propertyReader.getProperty("device.name"));
   			   capabilities.setCapability(MobileCapabilityType.VERSION, propertyReader.getProperty("device.os.version"));
   			   capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
   			   capabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);
   			   capabilities.setCapability("autoAcceptAlerts", true);
   			   
   			   capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.BROWSER);
   			   
   			   driver = new RemoteWebDriver(new URL(seleniumHubUrl),capabilities);
  			   driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  			   driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
      			   
  			   break;
      			   
   		   case "iossafari":
   			   
   			   capabilities = DesiredCapabilities.iphone();
  				
   			   capabilities.setCapability("browserName", "safari");
   			   capabilities.setCapability("platformName", "iOS");
   			   capabilities.setCapability("deviceName", propertyReader.getProperty("device.name"));
   			   capabilities.setCapability("platformVersion", propertyReader.getProperty("device.os.version"));
   			   capabilities.setCapability("takesScreenshot", "true");
   			   capabilities.setCapability("acceptSslCerts", "true");
   			   capabilities.setCapability("autoAcceptAlerts", "true");
   			   
   			   //UDID is required in case of iOS real device
   			    if(!propertyReader.getProperty("device.udid").trim().isEmpty()){
   			    	capabilities.setCapability("udid", propertyReader.getProperty("device.udid"));
   			    }
   			    
   			    capabilities.setCapability("bundleId", "com.bytearc.SafariLauncher");            				
   			    capabilities.setCapability("safariAllowPopups", true);
   			    capabilities.setCapability("safariOpenLinksInBackground", true);
   			    capabilities.setCapability("newCommandTimeout", 60);
   			    capabilities.setCapability("launchTimeout", 600000);
   				
   				driver = new RemoteWebDriver(new URL(seleniumHubUrl),capabilities);
   				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
   				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
   				
   				break;
   	     }    	  
      }
      catch(Exception e){
   	   e.printStackTrace();
      }
   	
   	return driver;
   }


    public void destroyDriver() {
        String identifier = Thread.currentThread().getName();
        WebDriver driver = driverMap.remove(identifier);
        if (driver != null) {
            driver.quit();
        }
    }
}