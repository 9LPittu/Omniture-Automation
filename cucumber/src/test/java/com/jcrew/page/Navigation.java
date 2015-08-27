package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Navigation {

    private final WebDriver driver;

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

    @FindBy(className = "icon-nav-close")
    private WebElement closeHamburgerMenu;

    @FindBy(className = "header__department-nav")
    private WebElement headerDepartmentNavigationSection;


    public Navigation(WebDriver driver) {
        this.driver = driver;
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
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(headerLogo));
        return headerLogo.isDisplayed();
    }

    public Point getLogoPosition() {
        return headerLogo.getLocation();
    }

    public void close_hamburger_menu() {
        closeHamburgerMenu.click();
    }

    public void click_on_stores_link() {
        storesLink.click();
    }

    public boolean isDepartmentLinkPresent(String department) {
        return headerDepartmentNavigationSection.findElement(By.linkText(department)).isDisplayed();
    }
}
