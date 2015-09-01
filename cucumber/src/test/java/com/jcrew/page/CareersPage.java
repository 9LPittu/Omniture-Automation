package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by 9hvenaga on 9/1/2015.
 */
public class CareersPage {

    @FindBy(xpath = "//h1[text()='Careers at J.Crew']")
    private WebElement careersAtJCrewHeader;

    public CareersPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isCareersPage() {
        return careersAtJCrewHeader.isDisplayed();
    }
}
