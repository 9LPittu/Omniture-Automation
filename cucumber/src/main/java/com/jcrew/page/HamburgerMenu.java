package com.jcrew.page;

import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HamburgerMenu {

    private static final String[] CATEGORY_MENU = {"Women", "Men", "Girls", "Boys"};
    private final StateHolder stateHolder = StateHolder.getInstance();
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(HamburgerMenu.class);
    @FindBy(className = "header__primary-nav__wrap")
    private WebElement hamburgerMenuLink;
    @FindBy(className = "js-primary-nav__link--menu")
    private WebElement hamburgerMenu;
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
    @FindBy(className = "menus--level1")
    private WebElement menuLevel1;

    public HamburgerMenu(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void click_on_hamburger_menu() {
        try {
            Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(hamburgerMenu));
            hamburgerMenu.click();
        } catch (StaleElementReferenceException sele) {
            click_on_hamburger_menu();
        }
    }

    public boolean isHamburgerMenuPresent() {
        boolean isIconDisplayed = hamburgerMenuLink.findElement(By.className("icon-header-menu")).isDisplayed();
        boolean isMenuTextDisplayed = hamburgerMenuLink.findElement(By.className("primary-nav__item--menu")).isDisplayed();
        return isIconDisplayed && isMenuTextDisplayed;
    }

    private void click_on_sign_in_link_from_hamburger_menu() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(signInLinkFromHamburger));
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

    public void click_on_back_link() {
        WebElement backLink = Util.createWebDriverWait(driver).until(
                ExpectedConditions.elementToBeClickable(menuLevel2.findElement(By.className("icon-arrow-back"))));
        backLink.click();
    }

    public boolean isCategoryPresent(String category) {
        return getCategory(category).isDisplayed();
    }

    public void click_on_category(String category) {
        getCategory(category).click();
    }

    private WebElement getCategory(String category) {
        WebElement element = categoryMenu.findElement(By.xpath(".//a[text()='" + category + "']"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    public void click_on_subcategory(String subcategory, String category) {
        getSubcategoryFromMenu(subcategory, category).click();
        Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains("category"));
    }

    private WebElement getSubcategoryFromMenu(String subcategory, String category) {
        WebElement categories = getMenuItemElementForCategory(category);
        WebElement categoryLink = categories.findElement(By.linkText(subcategory));

        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(categoryLink));

        return categoryLink;
    }

    private WebElement getMenuItemElementForCategory(String category) {
        return menuLevel2.findElement(By.xpath(".//div[contains(@class, 'menu__link--header') and " +
                "translate(text(), 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz') = " +
                "translate('" + category + "', 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz')]/.."));
    }

    public boolean isSubcategoryMenuLinkPresent(String subcategory) {
        return getSubcategoryFromMenu(subcategory, "").isDisplayed();
    }

    public void close_subcategory_hamburger_menu() {
        WebElement closeIcon = menuLevel2.findElement(By.className("icon-close"));
        closeIcon.click();
    }

    public void close_hamburger_menu() {
        WebElement iconClose = menuLevel1.findElement(By.className("icon-close"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(iconClose));
        iconClose.click();
    }

    public void click_random_category() {
        int index = Util.randomIndex(CATEGORY_MENU.length);
        WebElement category = getCategory(CATEGORY_MENU[(index)]);
        stateHolder.put("category", category.getAttribute("innerHTML"));
        category.click();
    }

    public void click_random_subcategory() {
        String categorySelected = (String) stateHolder.get("category");
        WebElement menuItemElement = getMenuItemElementForCategory(categorySelected);
        List<WebElement> menuItemLinks = menuItemElement.findElements(By.className("menu__link--has-href"));
        List<WebElement> selectableItems = new ArrayList<>();
        for (WebElement menuItemLink : menuItemLinks) {
            // currently subcategories containing feature are not working, skipping them for now.
            if (!menuItemLink.getAttribute("href").contains("feature")) {
                selectableItems.add(menuItemLink);
            }
        }
        int index = Util.randomIndex(selectableItems.size());
        WebElement subcategory = selectableItems.get(index);
        String subcategoryText = subcategory.getAttribute("innerHTML");
        stateHolder.put("subcategory", subcategoryText);
        logger.debug("Selected subcategory is {} from {} category", subcategoryText, categorySelected);
        subcategory.click();
    }

    public String getSignInMessage() {
        String result;
        if (signInLink.isDisplayed()) {
            result = signInLink.getText();
        } else {
            click_on_hamburger_menu();
            Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(signInLinkFromHamburger));
            result = signInLinkFromHamburger.getText();
        }
        return result;
    }
}
