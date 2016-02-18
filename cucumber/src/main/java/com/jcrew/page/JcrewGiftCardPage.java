package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JcrewGiftCardPage {

    private final WebDriver driver;

    @FindBy(id = "giftCard")
    private WebElement giftCardElement;

    public JcrewGiftCardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isJcrewGiftCardPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(giftCardElement));
        return giftCardElement.isDisplayed();
    }
}
