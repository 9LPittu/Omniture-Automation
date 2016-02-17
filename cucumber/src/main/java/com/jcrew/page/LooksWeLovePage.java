package com.jcrew.page;


import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class LooksWeLovePage {

    private final WebDriver driver;

    @FindBy(xpath = "//a[@class='section-button shop-now']")
    private List<WebElement> shopThisLookButtons;

    public LooksWeLovePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectRandomShopThisLook(){
        //wait for section with buttons to be loaded
        Util.createWebDriverWait(driver).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//a[@class='section-button shop-now']")));
        int randomIndex = Util.randomIndex(shopThisLookButtons.size());
        WebElement randomShopTheLook = shopThisLookButtons.get(randomIndex);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(randomShopTheLook));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(randomShopTheLook));
        randomShopTheLook.click();
        Util.waitLoadingBar(driver);
    }

}
