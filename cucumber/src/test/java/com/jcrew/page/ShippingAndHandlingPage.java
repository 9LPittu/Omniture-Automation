package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;

/**
 * Created by 9hvenaga on 9/1/2015.
 */
public class ShippingAndHandlingPage {
    @FindBy(className = "helpContents")
    WebElement helpContentWrap;

    public ShippingAndHandlingPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public boolean isShippingAndHandlingPage() {
        WebElement shippingText = helpContentWrap.findElement(By.xpath("//h2[text()='SHIPPING TO THE UNITED STATES']"));
        return shippingText.isDisplayed();
    }
}
