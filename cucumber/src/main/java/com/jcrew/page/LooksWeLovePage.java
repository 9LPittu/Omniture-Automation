package com.jcrew.page;


import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LooksWeLovePage {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(LooksWeLovePage.class);

    @FindBy(id = "tray__list")
    private WebElement trayList;

    public LooksWeLovePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectRandomShopThisLook(String type){
        logger.debug("Selecting a random shop the look for {}", type);
        Util.waitLoadingBar(driver);
        type = type.toLowerCase();
        By locator = By.xpath(".");

        //wait for section with buttons to be loaded
        switch (type){
            case "women":
                locator = By.xpath("//a[@class='section-button shop-now']");
                break;
            case "men":
                locator = By.xpath("//span[text()='SHOP THIS LOOK']");
                break;
            case "girls":
            case "boys":
                locator = By.xpath("//div[@id='plusMidWrapper']/div[contains(@class,'section')]/a");
                break;
            default:
                logger.debug("Not a valid type to select shop the look buttons...");
        }
        
        List<WebElement> buttons = driver.findElements(locator);
        if(!clickRandomShopThisLook(buttons)){
            logger.debug("Selected tray only contained one product, selecting look #0");
            buttons = driver.findElements(locator);
            clickShopThisLook(buttons.get(0));
        }
    }

    public boolean clickRandomShopThisLook(List<WebElement> buttons){
        int randomIndex = Util.randomIndex(buttons.size());
        WebElement randomShopTheLook = buttons.get(randomIndex);
        logger.debug("Picked look #{}", randomIndex);
        Util.waitLoadingBar(driver);
        Util.scrollToElement(driver,randomShopTheLook);
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(randomShopTheLook));
        String url = driver.getCurrentUrl();
        Util.clickOnElement(driver, randomShopTheLook);
        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));

        //Verify that you have more than one product in tray. If you only have one, then select other tray
        List<WebElement> items = trayList.findElements(By.className("js-tray__item"));
        logger.debug("items in tray: {}", items.size());
        if(items.size() == 1){
            driver.navigate().back();
            return false;
        }

        return true;
    }

    public void clickShopThisLook(WebElement button){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(button));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(button));
        button.click();
        Util.waitLoadingBar(driver);
    }

}
