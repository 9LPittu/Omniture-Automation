package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SocialResponsibilityPage {

    @FindBy(id = "landingSubhead")
    private WebElement landingSubHead;

    public SocialResponsibilityPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isSocialResponsibilityPage() {
        return landingSubHead.isDisplayed();
    }
}
