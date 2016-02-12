package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JcrewCreditCardPage {

    private final WebDriver driver;

    @FindBy(className = "hlp_apply")
    private WebElement helpApplySection;

    public JcrewCreditCardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public boolean isJcrewCreditCardPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(helpApplySection));
        return helpApplySection.isDisplayed();
    }
}
