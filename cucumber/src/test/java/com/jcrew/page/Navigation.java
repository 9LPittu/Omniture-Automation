package com.jcrew.page;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Navigation {


    @FindBy(className = "c-header__promo")
    private WebElement globalPromo;

    @FindBy(className = "primary-nav__item--bag")
    private WebElement bagSection;

    @FindBy(className = "js-primary-nav__link--search")
    private WebElement searchButton;

    @FindBy(className = "primary-nav__text--stores")
    private WebElement storesLink;

    @FindBy(id = "header__logo")
    private WebElement headerLogo;


    public Navigation(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isGlobalPromoDisplayed() {
        return globalPromo.isDisplayed();
    }

    public boolean isBagLinkDisplaying() {
        return bagSection.isDisplayed();
    }


    public boolean isSearchLinkDisplayed() {
        return searchButton.isDisplayed();
    }

    public void click_on_search_button() {
        searchButton.click();
    }

    public boolean isStoresLinkPresent() {
        return storesLink.isDisplayed();
    }

    public boolean isJCrewLogoPresent() {
        return headerLogo.isDisplayed();
    }

    public Point getLogoPosition() {
        return headerLogo.getLocation();
    }
}
