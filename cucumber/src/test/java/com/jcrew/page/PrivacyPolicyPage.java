package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PrivacyPolicyPage {

    @FindBy(className = "privacyUpdated")
    private WebElement privacyUpdatedElement;

    public PrivacyPolicyPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public boolean isPrivacyPolicyPage() {
        return privacyUpdatedElement.isDisplayed();
    }
}
