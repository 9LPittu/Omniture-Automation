package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class JcrewCreditCardPage {

    @FindBy(className = "hlp_apply")
    private WebElement helpApplySection;

    public JcrewCreditCardPage(WebDriver driver) {
        PageFactory.initElements(driver,this);
}

    public boolean isJcrewCreditCardPage() {
        return helpApplySection.isDisplayed();
    }
}
