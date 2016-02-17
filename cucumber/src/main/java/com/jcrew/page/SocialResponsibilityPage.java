package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SocialResponsibilityPage {

    @FindBy(id = "landingSubhead")
    private WebElement landingSubHead;

    private final WebDriver driver;

    public SocialResponsibilityPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isSocialResponsibilityPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(landingSubHead));
        return landingSubHead.isDisplayed();
    }
}
