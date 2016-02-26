package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ShippingAndHandlingPage {

    private final WebDriver driver;

    @FindBy(className = "helpContents")
    private
    WebElement helpContentWrap;

    public ShippingAndHandlingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public boolean isShippingAndHandlingPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(helpContentWrap));
        WebElement shippingText = helpContentWrap.findElement(By.xpath("//h2[text()='SHIPPING TO THE UNITED STATES']"));
        return shippingText.isDisplayed();
    }
}
