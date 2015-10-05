package com.jcrew.page;

import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class HamburgerMenu {

    private StateHolder stateHolder = StateHolder.getInstance();
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
    
    private static final String[] CATEGORY_MENU = {"WOMEN", "MEN", "GIRLS", "BOYS"};

    private Logger logger = LoggerFactory.getLogger(HamburgerMenu.class);

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
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(element));
    }

    public void click_on_back_link() {
        WebElement backlink = driver.findElement(By.className("icon-nav-back-arrow"));
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

    public void click_on_subcategory(String subcategory, String category) {
        getSubcategoryFromMenu(subcategory, category).click();
    }

    private WebElement getSubcategoryFromMenu(String subcategory, String category) {
        WebElement categoryElements = getMenuItemElementForCategory(category);

        WebElement subcategoryElement = categoryElements.findElement(By.linkText(subcategory));
        return subcategoryElement;
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
        WebElement closeIcon = menuLevel2.findElement(By.className("icon-nav-close"));
        closeIcon.click();
    }

    public void close_hamburger_menu() {
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(closeHamburgerMenu));
        closeHamburgerMenu.click();
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
}
