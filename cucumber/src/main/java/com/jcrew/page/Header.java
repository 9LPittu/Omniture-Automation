package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Header {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(Header.class);

    @FindBy(className = "header__primary-nav__wrap")
    private WebElement headerWrap;
    @FindBy(className = "icon-close")
    private WebElement searchCloseIcon;
    @FindBy(id = "js-header__logo")
    private WebElement headerLogo;
    @FindBy(className = "c-header__breadcrumb")
    private WebElement breadcrumbSection;
    @FindBy(className = "js-primary-nav__link--search")
    private WebElement searchButton;
    @FindBy(className = "primary-nav__text--stores")
    private WebElement storesLink;
    @FindBy(css = ".primary-nav__item--bag > .primary-nav__link")
    private WebElement shoppingBagLink;
    @FindBy(css = ".icon-header.icon-header-bag.icon-bag")
    private WebElement bagIcon;
    @FindBy(id = "section1")
    private WebElement genderLandingSection;


    public Header(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isHeaderLinkPresent(String headerLink) {
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(headerWrap.findElement(By.linkText(headerLink))));

        return true;
    }

    public boolean isHeaderBagIconPresent() {
        try {
            return bagIcon.isDisplayed();
        } catch (StaleElementReferenceException sere) {
            return isHeaderBagIconPresent();
        }
    }

    public String getBagIconLinkText() {
        try {
            return driver.findElement(By.className("primary-nav__item--bag")).getText();
        } catch (StaleElementReferenceException sere) {
            return getBagIconLinkText();
        }
    }

    public boolean isSearchDrawerOpen() {
        WebElement headerSearchInput = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(headerWrap.findElement(By.className("header__search__input"))));
        return headerSearchInput.isDisplayed();
    }

    public boolean isSearchDrawerClosed() {
        return Util.createWebDriverWait(driver).until(
                ExpectedConditions.invisibilityOfElementLocated(By.className("header__search__input")));
    }

    public void click_on_search_close_icon() {
        WebElement searchIconClose = headerWrap.findElement(By.className("js-header__search__button--close"));
        searchIconClose.click();
    }

    public String getSearchDrawerTerm() {
        return driver.getCurrentUrl();
    }

    public List<String> getPrimaryNavigationLinkNames() {
        List<String> primaryNavigationLinkNames = new ArrayList<>();

        List<WebElement> headerWrapElements = headerWrap.findElements(By.className("primary-nav__link"));

        for (WebElement headerElement : headerWrapElements) {
            primaryNavigationLinkNames.add(headerElement.getText());
        }

        return primaryNavigationLinkNames;
    }

    public boolean isJCrewLogoPresent() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerLogo));
        return headerLogo.isDisplayed();
    }



    public Point getLogoPosition() {
        return headerLogo.getLocation();
    }

    public void click_jcrew_logo() {
        Util.clickWithStaleRetry(headerLogo);
       // Util.waitLoadingBar(driver);
    }

    public void click_on_search_button() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    public boolean isSearchLinkDisplayed() {
        return searchButton.isDisplayed();
    }

    public boolean isStoresLinkPresent() {
        return storesLink.isDisplayed();
    }

    public void click_on_stores_link() {
        storesLink.click();
    }

    public void click_item_bag() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(shoppingBagLink));
        shoppingBagLink.click();
    }

    public boolean isBagLinkDisplaying() {
        return shoppingBagLink.isDisplayed();
    }

    public void click_breadcrumb(String breadcrumb) {
        Util.waitWithStaleRetry(driver,breadcrumbSection);
        WebElement breadcrumbElement = breadcrumbSection.findElement(
                By.xpath("//a[text()='" + breadcrumb + "' and @class='breadcrumb__link']"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(breadcrumbElement));
        Util.clickWithStaleRetry(breadcrumbElement);
       // Util.waitLoadingBar(driver);
    }

    public boolean isGenderLandingPage(String gender){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(genderLandingSection));
        WebElement genderPageElement =genderLandingSection.findElement(By.xpath("//h2[contains(text(),'NEW FOR "+gender.toUpperCase()+"')]"));
        return genderPageElement.isDisplayed();
    }

    public boolean isJcrewBreadCrumbNotDisplayed() {
        try {
            return !breadcrumbSection.isDisplayed();
        } catch (NoSuchElementException e) {
            logger.debug("bread crumb section not present");
            return true;
        }
    }

    public boolean isEmbeddedHeaderSectionDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerWrap));
        return headerWrap.isDisplayed();
    }
}
