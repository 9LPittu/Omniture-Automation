package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CareersPage {

    private final WebDriver driver;
    @FindBy(xpath = "//h1[text()='Careers at J.Crew']")
    private WebElement careersAtJCrewHeader;

    public CareersPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isCareersPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(careersAtJCrewHeader));
        return careersAtJCrewHeader.isDisplayed();
    }
}
