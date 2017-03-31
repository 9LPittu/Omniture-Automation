package com.jcrew.page.header;

import com.jcrew.page.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by ngarcia on 3/2/17.
 */
public class HeaderLogo extends HeaderWrap {

    @FindBy(id = "js-header__logo")
    private WebElement header_logo;

    public HeaderLogo(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(header_logo));
    }

    public void clickLogo() {
        header_logo.click();

    }

    public boolean isLogoVisible() {
        return header_logo.isDisplayed();
    }

    public void hoverLogo() {
        hoverAction.moveToElement(header_logo);
        hoverAction.perform();
    }

}
