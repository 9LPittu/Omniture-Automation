package com.jcrew.utils;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.jcrew.pojo.Country;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);
    public final static StateHolder stateHolder = StateHolder.getInstance();

    public static final int DEFAULT_TIMEOUT = 60;
    public static final String xpathGetTextLower = "translate(text(), 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz')";
    
    public static final String UP = "up";
    public static final String DOWN = "down";
    public static String e2eErrorMessages= "";
    
    public static String getEnvironment(){
    	return System.getProperty("environment", "ci");
    }

    public static int randomIndex(int size) {
        return (int) Math.floor(Math.random() * size);
    }

    public static WebElement randomIndex(List<WebElement> list){
        int random = randomIndex(list.size());
        return list.get(random);
    }

    public static WebDriverWait createWebDriverWait(WebDriver driver) {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }
    
    public static WebDriverWait createWebDriverWait(WebDriver driver, int timeInSeconds) {
        return new WebDriverWait(driver, timeInSeconds);
    }
    
    public static void scrollToElement(WebDriver driver, WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
    }
    
    public static void waitForPageReady(WebDriver driver) {
        createWebDriverWait(driver).until(new Predicate<WebDriver>() {
            public boolean apply(WebDriver driver) {
                boolean result = ((long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                logger.info("document.readyState returned {}", result);
                return result;
            }
        });
    }
    
    public static void waitForPageFullyLoaded(WebDriver driver) {
        createWebDriverWait(driver).until(new Predicate<WebDriver>() {
            public boolean apply(WebDriver driver) {
                String complete = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState");
                logger.info("document.readyState returned {}", complete);
                return complete.equals("complete");
            }
        });
    }

    public static void waitLoadingBar(WebDriver driver){
        try {
            createWebDriverWait(driver).until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver webDriver) {
                    WebElement html = webDriver.findElement(By.tagName("html"));
                    String htmlClass = html.getAttribute("class");
                    return !htmlClass.contains("nprogress-busy");
                }
            });
        } catch (StaleElementReferenceException stale) {
            logger.error("StaleElementReferenceException when waiting for loading bar. " +
                    "Assuming it is gone and ignoring this exception");
        }
    }

    public static void clickWithStaleRetry(WebElement element) throws StaleElementReferenceException{
        int attempts = 0;
        boolean success = false;

        while (attempts < 2 && !success){
            try{
                element.click();
                success = true;
            } catch (StaleElementReferenceException staleException){
                logger.debug("Stale Element Exception when retrying to click");
            }
            attempts++;
        }

        if(!success){
            throw new StaleElementReferenceException("Failed to click element");
        }
    }

    public static void waitWithStaleRetry(WebDriver driver, WebElement element) throws StaleElementReferenceException{
        int attempts = 0;
        boolean success = false;
        WebDriverWait wait = createWebDriverWait(driver);

        while (attempts < 4 && !success){
            try{
                wait.until(ExpectedConditions.visibilityOf(element));
                success = true;
            } catch (StaleElementReferenceException staleException){
            	wait(3000);
                logger.debug("Stale Element Exception when retrying to wait");
            }
            attempts++;
        }

        if(!success){
            throw new StaleElementReferenceException("Failed to wait element");
        }
    }

    public static boolean countryContextURLCompliance(WebDriver driver, Country country) {
        String url = driver.getCurrentUrl();
        String countryURL = country.getHomeurl();
        String countryCode = country.getCountry();

        boolean startsWith = url.startsWith(countryURL);
        boolean contains = url.contains("/" + countryCode + "/");

        return startsWith & contains == country.isContexturl();
    }

    public static boolean countryContextURLCompliance(WebDriver driver, Country country, String pattern) {
        String url = driver.getCurrentUrl();
        String countryURL = country.getHomeurl();
        String countryCode = country.getCountry();

        boolean startsWith = url.startsWith(countryURL);
        boolean contains = url.contains("/" + countryCode + "/");
        boolean hasPattern = url.contains(pattern);

        return startsWith & contains == country.isContexturl() & hasPattern;
    }

    public static void checkoutNext(WebDriver driver, WebElement checkoutButton) {
        PropertyReader reader = PropertyReader.getPropertyReader();
        String browser = reader.getProperty("browser");

        if("desktop".equals(browser)) {
            String href = checkoutButton.getAttribute("href");
            try {
                href = URLDecoder.decode(href, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("not able to decode!", e);
            }
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript(href);
        } else {
            checkoutButton.click();
        }
    }
    
    public static String logBrowserErrors(WebDriver driver) {
        Logs errorlog = driver.manage().logs();
        LogEntries errors = errorlog.get(LogType.BROWSER);
        String errorMessage = "";

        for (LogEntry error : errors) {
            logger.error("Browser logs {}", error.getMessage());
            errorMessage += error.getMessage() + "\n";
        }

        if(!errorMessage.isEmpty())
            errorMessage = "Browser log: \n" + errorMessage;

        return errorMessage;
    }

    public static String getStringConsoleVariable(WebDriver driver, String variable) {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        String value = (String) je.executeScript("return " + variable);

        logger.info("{}: {}", variable, value);

        return value;
    }

    public static boolean getBooleanConsoleVariable(WebDriver driver, String variable) {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        boolean value = false;
        try {
            value = (boolean) je.executeScript("return " + variable);

            logger.info("{}: {}", variable, value);

        } catch (WebDriverException wde) {
            logger.info("Unable to get boolean variable {}; assuming false", variable);
        }

        return value;
    }

    public static void wait(int waitTime) {
        Boolean iterate = true;
        Calendar calendar =Calendar.getInstance();
        long startTime = calendar.getTimeInMillis();
        do {
            Calendar calendar1 =Calendar.getInstance();
            long currentTime = calendar1.getTimeInMillis();
            long timeDifference = currentTime - startTime;
            iterate = timeDifference < waitTime;
        }while(iterate);
    }    
    
    public static void waitSpinningImage(WebDriver driver){
        try {
            createWebDriverWait(driver).until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver webDriver) {
                    WebElement spinningImage = webDriver.findElement(By.xpath("//div[contains(@class,'global__spinner-img')]"));
                    String spinningImageClass = spinningImage.getAttribute("class");
                    return spinningImageClass.contains("is-hidden");
                }
            });
        } catch (StaleElementReferenceException stale) {
            logger.error("StaleElementReferenceException when waiting for spinning image. " +
                    "Assuming it is gone and ignoring this exception");
        }
    }    
      
    public static void scrollAndClick(WebDriver driver, WebElement element){
    	int cntr = 0;
        do{
        	try{
        		scrollToElement(driver, element);
        		element.click();
        		break;
        	}
        	catch(WebDriverException e){
                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript("arguments[0].scrollIntoView();", element);

                cntr++;
        	}

        }while(cntr <= 4);
    }
    
    public static void scrollPage(WebDriver driver, String pagePosition) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        if (pagePosition.equalsIgnoreCase(UP)) {
            jse.executeScript("scrollBy(0, -400)", "");
        } else if (pagePosition.equalsIgnoreCase(DOWN)) {
            jse.executeScript("scrollBy(0, 400)", "");
        }
    }
    
    public static String getPageVariableValue(WebDriver driver, final String variable) throws InterruptedException {
        WebDriverWait waitForVariable = new WebDriverWait(driver, 10);
        String value = "";

        try {
            value = waitForVariable.until(new Function<WebDriver, String>() {
                @Override
                public String apply(WebDriver webDriver) {
                    String value;
                    try {
                        value = (String) ((JavascriptExecutor) webDriver).executeScript("return " + variable);
                        if (value != null && value.isEmpty())
                            value = null;
                    } catch (WebDriverException wde) {
                        value = null;
                    }
                    return value;
                }
            });
        } catch (TimeoutException timeout) {
            logger.error("Variable {} not found in URL {} after waiting", variable, driver.getCurrentUrl(), timeout);
        }

        return value;
    }
    
    public static List<String> getText(List<WebElement> list) {
        List<String> text = new ArrayList<>(list.size());

        for(WebElement element : list) {
            text.add(element.getText().trim().toLowerCase());
        }

        return text;
    }

    public static Map<String, String> getPageVariablesValue(WebDriver driver, Set<String> variables) throws InterruptedException {
        Map<String, String> variable_value = new HashMap<>();

        Iterator<String> variablesIterator = variables.iterator();
        String firstVariable = variablesIterator.next();

        variable_value.put(firstVariable, getPageVariableValue(driver, firstVariable));

        while (variablesIterator.hasNext()) {
            String variable = variablesIterator.next();
            String value;
            try {
                value = (String) ((JavascriptExecutor) driver).executeScript("return " + variable);
            } catch (WebDriverException wde) {
                value = "";
                logger.error("Not able to get variable {}", variable, wde);
            }
            variable_value.put(variable, value);
        }

        return variable_value;

    }
    
    public static void e2eErrorMessagesBuilder(String errorMessageText){
		if(stateHolder.hasKey("e2e_error_messages")){
			e2eErrorMessages = stateHolder.get("e2e_error_messages");
		}
		
		e2eErrorMessages += errorMessageText + "\n\n";
		stateHolder.put("e2e_error_messages", e2eErrorMessages);
	}
}
