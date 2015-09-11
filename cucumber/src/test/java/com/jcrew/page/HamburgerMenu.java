package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HamburgerMenu {

    private final WebDriver driver;

    @FindBy(className = "js-primary-nav__link--menu")
    private WebElement hamburgerMenuLink;

    @FindBy(className = "menu__nested")
    private WebElement nestedMenu;

    @FindBy(css = ".primary-nav__item--account > a")
    private WebElement signInLink;

    @FindBy(css = "#c-nav__userpanel > a")
    private WebElement signInLinkFromHamburger;

    @FindBy(className = "c-menu")
    private WebElement categoryMenu;

    @FindBy(className = "menus--level2")
    private WebElement menuLevel2;

    @FindBy(xpath = "//a[contains(@class, 'menu__link--has-href') and @href='/c/womens_category/shirtsandtops']")
    private WebElement womenShirtAndTopsCategoryLink;

    @FindBy(xpath = "//a[contains(@class, 'menu__link--has-href') and @href='/c/womens_category/sweaters']")
    private WebElement womenSweatersCategoryLink;

    @FindBy(className = "icon-nav-close")
    private WebElement closeHamburgerMenu;

    public HamburgerMenu(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void click_on_hamburger_menu() {
        waitForVisibility(hamburgerMenuLink);
        hamburgerMenuLink.click();
    }

    public boolean isHamburgerMenuPresent() {
        boolean isIconDisplayed = hamburgerMenuLink.findElement(By.className("icon-header-menu")).isDisplayed();
        boolean isMenuTextDisplayed = hamburgerMenuLink.findElement(By.className("primary-nav__text")).isDisplayed();
        return isIconDisplayed && isMenuTextDisplayed;
    }

    public void click_on_sign_in_link_from_hamburger_menu() {
        waitForVisibility(signInLinkFromHamburger);
        signInLinkFromHamburger.click();
    }

    public void click_on_sign_in_link() {
        if (signInLink.isDisplayed()) {
            signInLink.click();
        } else {
            click_on_hamburger_menu();
            click_on_sign_in_link_from_hamburger_menu();
        }
    }

    private void waitForVisibility(WebElement element) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
    }

    public void click_on_shirts_and_tops_from_women_category_in_hamburger_menu() {
        waitForVisibility(womenShirtAndTopsCategoryLink);
        womenShirtAndTopsCategoryLink.click();
    }


    public void click_on_back_link() {
        WebElement backlink = driver.findElement(By.xpath("//*[@id='global__nav']/div/div[2]/button/div"));
        waitForVisibility(backlink);
        backlink.click();
    }

    public boolean isCategoryPresent(String category) {
        return getCategory(category).isDisplayed();
    }

    public void click_on_category(String category) {
        getCategory(category).click();
    }

    private WebElement getCategory(String category) {
        return categoryMenu.findElement(By.linkText(category));
    }

    public void click_on_subcategory(String subcategory) {
        getSubcategoryFromMenu(subcategory).click();
    }

    private WebElement getSubcategoryFromMenu(String subcategory) {
        return nestedMenu.findElement(By.linkText(subcategory));
    }

    public boolean isSubcategoryMenuLinkPresent(String subcategory) {
        return getSubcategoryFromMenu(subcategory).isDisplayed();
    }

    public void close_subcategory_hamburger_menu() {
        WebElement closeIcon = menuLevel2.findElement(By.className("icon-nav-close"));
        closeIcon.click();
    }

    public void close_hamburger_menu() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(closeHamburgerMenu));
        closeHamburgerMenu.click();
    }

}
