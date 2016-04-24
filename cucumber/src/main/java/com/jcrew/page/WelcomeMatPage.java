package com.jcrew.page;

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

    @FindBy(className = "c-header__welcomemat--country-context")
    private WebElement countryContext;

    @FindBy(className = "c-header__welcomemat--flag")
    private WebElement countryFlag;

    private final StateHolder stateHolder = StateHolder.getInstance();

    public WelcomeMatPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean  isWelcomeMatDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(welcomeMat));
        return welcomeMat.isDisplayed();
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
        return driver.findElement(By.className("c-header__welcomemat--logo")).isDisplayed();
    }


    public boolean isWelcomeHeaderMessageDisplayed(String msg1, String msg2) {

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


    public boolean isWelcomeMatContentDisplayed() {
        String internationalCountry = countryContext.getText();
        if (internationalCountry.equalsIgnoreCase("Canada")) {
            return driver.findElement(By.className("c-header__welcomematCanada--body")).isDisplayed();
        }
            else return driver.findElement(By.className("c-header__welcomemat--body")).isDisplayed();

    }

    public void click_on_start_shopping(String link)  {
        driver.findElement(By.linkText(link)).click();
    }

    public boolean isFlagAndNameDisplayedCorrectly() {

        String expectedCountryName = (String)stateHolder.get("selectedCountry");
        String expectedCountryFlag = expectedCountryName.replaceAll("\\s", "").toLowerCase();
        logger.info("is in the expected country context",countryContext.getText());
        boolean flag = countryFlag.getAttribute("class").contains(expectedCountryFlag);
        System.out.println(flag);
        return countryContext.getText().equalsIgnoreCase(expectedCountryName) && flag;
    }

}
