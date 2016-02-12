package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RequestACatalogPage {

    private final WebDriver driver;

    @FindBy(id="firstName")
    private WebElement firstNameInputElement;

    public RequestACatalogPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isRequestACatalogPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(firstNameInputElement));
        return firstNameInputElement.isDisplayed();
    }
}
