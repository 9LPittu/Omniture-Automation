package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReturnsAndExchangesPage {

    @FindBy(className = "helpContents")
    private WebElement helpContentWrap;

    public ReturnsAndExchangesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isReturnsAndExchangePage() {
        WebElement shippingText = helpContentWrap.findElement(By.xpath("//h2[text()='RETAIL STORE PURCHASES']"));
        return shippingText.isDisplayed();
    }
}
