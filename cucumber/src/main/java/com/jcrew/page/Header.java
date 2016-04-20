package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.util.StateHolder;
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
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final String menuItems[] = {"MENU", "SEARCH", "STORES","BAG"};

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
    @FindBy(id = "js-header__cart")
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
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerWrap));
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
        Util.waitLoadingBar(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("js-footer__fullsite__link")));
        Util.clickWithStaleRetry(headerLogo);
    }

    public void click_on_search_button() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("js-footer__fullsite__link")));
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
        WebElement breadcrumbElement = Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='" + breadcrumb + "' and @class='breadcrumb__link']")));
        breadcrumbElement.click();
    }

    public boolean isGenderLandingPage(String gender){
        Country country = (Country) stateHolder.get("context");
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(genderLandingSection));
        WebElement genderPageElement =genderLandingSection.findElement(By.xpath("//h2[contains(text(),'NEW FOR "+gender.toUpperCase()+"')]"));

        boolean isDisplayed = genderPageElement.isDisplayed();
        boolean isURL = Util.countryContextURLCompliance(driver,country);

        return isDisplayed & isURL;
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

    public boolean isAllMenuItemsDisplayed(){
        boolean result = true;

        for(String item:menuItems){
            result &= isHeaderLinkPresent(item);
        }

        return result;
    }

    public String getBagButtonLink(){
        return shoppingBagLink.getAttribute("href");
    }

    public String getStoresButtonLink(){
        WebElement stores = driver.findElement(By.cssSelector(".primary-nav__item--stores > .primary-nav__link"));
        return stores.getAttribute("href");
    }
}
