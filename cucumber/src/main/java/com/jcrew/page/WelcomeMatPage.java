package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 9hvenaga on 4/15/2016.
 */
public class WelcomeMatPage {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(WelcomeMatPage.class);

    @FindBy(className = "c-header__welcomemat")
    private WebElement welcomeMat;
    
    @FindBy(className = "c-header__welcomemat--logo")
    private WebElement welcomeMatLogo;

    @FindBy(xpath = "//span[@class='c-header__welcomemat--country']")
    private WebElement countryContext;

    @FindBy(className = "c-header__welcomemat--flag")
    private WebElement countryFlag;

    private final StateHolder stateHolder = StateHolder.getInstance();

    public WelcomeMatPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean  isWelcomeMatDisplayed() {
    	try{
	        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(welcomeMat));
	        return welcomeMat.isDisplayed();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }


    public boolean isWelcomeMatNotDisplayed() {
        try {
            return !(welcomeMat.isDisplayed());
        } catch(NoSuchElementException ne) {
            logger.debug("welcome mat is not present");
            return true;
        }
    }

    public boolean isJcrewLogoDisplayed() {
    	try{
    		WebElement element = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(welcomeMatLogo));
    		return element.isDisplayed();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }


    public boolean isWelcomeHeaderMessageDisplayed(String msg1, String msg2) {
    	try{
	        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(countryContext));
	        String internationalCountry = countryContext.getText();
	        logger.info(internationalCountry);
	        WebElement msgHeader = driver.findElement(By.className("c-header__welcomemat--header"));
	        if (internationalCountry.equalsIgnoreCase("Canada")) {
	             logger.info("country is canada");
	            return msgHeader.getText().contains(msg1);
	        } else {
	            logger.info("not canada");
	            return msgHeader.getText().contains(msg2);
	        }
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }


    public boolean isWelcomeMatContentDisplayed() {
    	try{
	    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(countryContext));
	        String internationalCountry = countryContext.getText();
	        
	        WebElement welcomeMat;
	        if (internationalCountry.equalsIgnoreCase("Canada")) {
	        	welcomeMat = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='c-header__welcomematCanada--body']")));
	        }
	        else{
	        	welcomeMat = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='c-header__welcomemat--body']")));
	        }
	        
	        return welcomeMat.isDisplayed();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }

    public void click_on_start_shopping(String link)  {
        driver.findElement(By.linkText(link)).click();
        Util.waitLoadingBar(driver);
    }

    public boolean isFlagAndNameDisplayedCorrectly() {
    	try{
	        Country c = (Country)stateHolder.get("context");
	        String expectedCountryName = c.getCountryName();
	        String expectedCountryFlag = expectedCountryName.replaceAll("\\s", "").toLowerCase();
	        logger.info("is in the expected country context",countryContext.getText());
	        boolean flag = countryFlag.getAttribute("class").contains(expectedCountryFlag);
	        logger.info("Is flag displayed? {}", flag);
	        return countryContext.getText().equalsIgnoreCase(expectedCountryName) && flag;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }

    public boolean isInInitialInternationalPage() {
    	try{
	        String expectedUrl = (String)stateHolder.get("randomUrl");
	        String pageUrl = (String)stateHolder.get("pageUrl");
	        logger.info("expected url  {}",expectedUrl);
	        String currentUrl = driver.getCurrentUrl();
	        logger.info("current url  {}",currentUrl);
	        return currentUrl.equals(expectedUrl)&& currentUrl.contains(pageUrl);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }
}
