package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Header {

    @FindBy(className = "header__primary-nav__wrap")
    private WebElement headerWrap;

    public Header(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isHeaderLinkPresent(String headerLink) {
        return headerWrap.findElement(By.linkText(headerLink)).isDisplayed();
    }

    public boolean isSearchBoxDisplayed() {
        return headerWrap.findElement(By.className("header__search__input")).isDisplayed();
    }
}
