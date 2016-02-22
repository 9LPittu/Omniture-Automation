package com.jcrew.page;


import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LooksWeLovePage {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(LooksWeLovePage.class);

    public LooksWeLovePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectRandomShopThisLook(String type){
        List<WebElement> buttons = null;
        WebDriverWait wait = Util.createWebDriverWait(driver);
        type = type.toLowerCase();

        //wait for section with buttons to be loaded
        switch (type){
            case "women":
                buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//a[@class='section-button shop-now']")));
                break;
            case "men":
                buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("image-box__link")));
                break;
            case "girls":
            case "boys":
                buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//a[contains(@class,'section-image')]")));
                break;
            default:
                logger.debug("Not a valid type to select shop the look buttons...");

        }

        clickRandomShopThisLook(buttons);
    }

    public void clickRandomShopThisLook(List<WebElement> buttons){
        int randomIndex = Util.randomIndex(buttons.size());
        WebElement randomShopTheLook = buttons.get(randomIndex);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(randomShopTheLook));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(randomShopTheLook));
        randomShopTheLook.click();
        Util.waitLoadingBar(driver);
    }

}
