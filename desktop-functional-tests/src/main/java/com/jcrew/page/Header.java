package com.jcrew.page;

import com.google.common.base.Predicate;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Header {

	private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(Header.class);
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final String menuItems[] = {"MENU", "SEARCH", "STORES","BAG"};

    @FindBy(id = "js-header__cart")
    private WebElement shoppingBagLink;
    
    @FindBy(className = "header__primary-nav__wrap")
    private WebElement headerWrap;
    
    @FindBy(className = "js-primary-nav__link--search")
    private WebElement searchButton;
    
    @FindBy(css = ".icon-header.icon-header-bag.icon-bag")
    private WebElement bagIcon;

    public Header(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void click_item_bag() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(shoppingBagLink));
        shoppingBagLink.click();
    }
    
    public boolean isHeaderLinkPresent(String headerLink) {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerWrap));
        Util.createWebDriverWait(driver).until(
            ExpectedConditions.visibilityOf(headerWrap.findElement(By.linkText(headerLink))));

        return true;
    }
    
    public boolean isBagLinkDisplaying() {
        return shoppingBagLink.isDisplayed();
    }
    
    public void click_on_search_button() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("js-footer__fullsite__link")));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }
    
    public boolean isSearchDrawerOpen() {
        WebElement headerSearchInput = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(headerWrap.findElement(By.className("header__search__input"))));
        return headerSearchInput.isDisplayed();
    }
    
    public String getStoresButtonLink(){
        WebElement stores = driver.findElement(By.cssSelector(".primary-nav__item--stores > .primary-nav__link"));
        return stores.getAttribute("href");
    }
    
    public boolean isHeaderBagIconPresent() {
        try {
            return bagIcon.isDisplayed();
        } catch (StaleElementReferenceException sere) {
            return isHeaderBagIconPresent();
        }
    }
    
    public String getBagButtonLink(){
        return shoppingBagLink.getAttribute("href");
    }
}
