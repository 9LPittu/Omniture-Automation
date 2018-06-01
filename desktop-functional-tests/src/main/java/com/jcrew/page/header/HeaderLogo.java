package com.jcrew.page.header;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by ngarcia on 3/2/17.
 */
public class HeaderLogo extends HeaderWrap {

    @FindBy(id = "js-header__logo")
    private WebElement header_logo;

    public HeaderLogo(WebDriver driver) {
        super(driver);
       // wait.until(ExpectedConditions.visibilityOf(header_logo));
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
