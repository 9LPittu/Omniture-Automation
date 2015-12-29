package com.jcrew.page;

import java.util.List;

import com.jcrew.util.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShippingMethodPage {

    private final WebDriver driver;

    private final Logger logger = LoggerFactory.getLogger(ShippingMethodPage.class);

    @FindBy(id = "method0")
    private WebElement economyUps;

    @FindBy(id = "noGifts")
    private WebElement noGifts;

    @FindBy(className = "button-submit")
    private WebElement continueButton;

    @FindBy(className = "shippingmethod-container")
    private WebElement shippingMethodContainer;

    @FindBy(className = "footer__row--bottom")
    private WebElement footerRowBottom;
    
    @FindBys({@FindBy(name="shippingMethod")})
    public WebElement shippingMethodsRadioButtons;

    public ShippingMethodPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void click_continue_button() {
        try {
            Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(continueButton));
            continueButton.click();
        } catch(StaleElementReferenceException e) {
            click_continue_button();
        }
    }

    public boolean isEconomyUps() {
        return economyUps.isSelected();
    }

    public boolean isNoGifts() {
        return noGifts.isSelected();
    }


    public boolean isShippingMethodPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(shippingMethodContainer));
        return true;
    }

    public boolean isPageLoaded() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(footerRowBottom));
        return true;
    }
    
    public void selectShippingMethod(){
    	List<WebElement> shippingMethodsRadioButtons = driver.findElements(By.name("shippingMethod"));
    	WebElement shippingMethod = shippingMethodsRadioButtons.get(Util.randomIndex(shippingMethodsRadioButtons.size()));
    	shippingMethod.click();
    }
}
