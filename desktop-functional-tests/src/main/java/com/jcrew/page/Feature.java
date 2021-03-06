package com.jcrew.page;

import java.util.List;

import com.jcrew.page.header.HeaderWrap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.jcrew.utils.Util;

public class Feature extends PageObject{

    @FindBy(id = "categoryPlus")
    private WebElement body;

    public Feature(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        
        wait.until(ExpectedConditions.visibilityOf(body));
        
        Footer footer = new Footer(driver);
        HeaderWrap header = new HeaderWrap(driver);
    }
    
    public void selectRandomShopThisLook() {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("plusMidWrapper")));
    	List<WebElement> shopThisLook = driver.findElements(By.xpath("//a[" + Util.xpathGetTextLower + "=" + "'shop this look']"));
    	int randomIndex = Util.randomIndex(shopThisLook.size());
    	
    	WebElement randomShopTheLook = shopThisLook.get(randomIndex);
    	Util.scrollAndClick(driver, randomShopTheLook);
    	Util.waitLoadingBar(driver);
    }

   
}
