package com.jcrew.page;

import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HamburgerMenu {

	private static final String[] CATEGORY_MENU = {"Women", "Men", "Girls", "Boys", "Wedding"};
	
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

    @FindBy(id = "global__nav")
    private WebElement globalNav;

    @FindBy(className = "menus--level2")
    private WebElement menuLevel2;
    
    @FindBy(xpath = "//a[contains(@class, 'menu__link--has-href') and @href='/c/womens_category/shirtsandtops']")
    private WebElement womenShirtAndTopsCategoryLink;
    
    @FindBy(xpath = "//a[contains(@class, 'menu__link--has-href') and @href='/c/womens_category/sweaters']")
    private WebElement womenSweatersCategoryLink;
    
    @FindBy(className = "menus--level1")
    private WebElement menuLevel1;
    
    @FindBy(className = "c-sale__c-category-list")
    private WebElement saleCategoryList;

    public HamburgerMenu(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void click_on_hamburger_menu() {
        WebDriverWait wait = Util.createWebDriverWait(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("js-footer__fullsite__link")));
        Util.waitWithStaleRetry(driver,hamburgerMenu);
        wait.until(ExpectedConditions.elementToBeClickable(hamburgerMenu));
        Util.clickWithStaleRetry(hamburgerMenu);
        wait.until(ExpectedConditions.visibilityOf(globalNav));
    }

    public boolean isHamburgerMenuPresent() {
        boolean isIconDisplayed = hamburgerMenuLink.findElement(By.className("icon-header-menu")).isDisplayed();
        boolean isMenuTextDisplayed = hamburgerMenuLink.findElement(By.className("primary-nav__item--menu")).isDisplayed();
        return isIconDisplayed && isMenuTextDisplayed;
    }

    public void click_on_sign_in_link_from_hamburger_menu() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(signInLinkFromHamburger));
        signInLinkFromHamburger.click();
    }

    public void click_on_sign_in_link() {
        click_on_hamburger_menu();
        click_on_sign_in_link_from_hamburger_menu();
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
        String url = driver.getCurrentUrl();

        getCategory(category).click();        

        if("sale".equalsIgnoreCase(category)){
            Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
        } else {
            Util.waitLoadingBar(driver);
        }
        
        logger.debug("'{}' category is clicked", category);
	    stateHolder.put("category", category);
    }

    private WebElement getCategory(String category) {
    	WebElement element = categoryMenu.findElement(By.xpath(".//a[" + Util.xpathGetTextLower + "='" + category.toLowerCase() + "']"));    	
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    public void click_on_subcategory(String subcategory, String category) {
    	String currentURL = driver.getCurrentUrl();
        getSubcategoryFromMenu(subcategory, category).click();
        logger.info("Actual subcategory clicked: {}", subcategory);
        stateHolder.put("subcategory", subcategory);
        Util.waitLoadingBar(driver);
    }

    public void click_on_selected_featured_this_month(String choice) {
        WebElement level3Menus = driver.findElement(
                By.xpath("//div[@class='c-menus menus--level3 js-menus--level3']/div[@class='menu__item is-lazy-loaded']"));
        WebElement looksWeLove = level3Menus.findElement(
                    By.xpath(".//span[@class='menu__link__label' and contains(text(),'" + choice + "')]"));

        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(looksWeLove));
        looksWeLove.click();
    }

    public void click_on_sale_subcategory(String subcategory) {
        Util.waitLoadingBar(driver);
        getSubcategoryFromSale(subcategory).click();
        stateHolder.put("sale category", subcategory);
        Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains("search"));
        Util.waitLoadingBar(driver);
    }

    private WebElement getSubcategoryFromMenu(String subcategory, String category) {
        WebElement categories = getMenuItemElementForCategory(category);

        Util.createWebDriverWait(driver).until(ExpectedConditions.textToBePresentInElement(categories,subcategory));

        WebElement categoryLink = categories.findElement(By.linkText(subcategory));

        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(categoryLink));
        return categoryLink;
    }

    private WebElement getSubcategoryFromSale(String subcategory) {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(saleCategoryList));
        return saleCategoryList.findElement(By.xpath(".//div[@class='c-category__header accordian__header' and " +
                Util.xpathGetTextLower + " = " +
                "translate('" + subcategory + "', 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz')]/.."));
    }

    private WebElement getMenuItemElementForCategory(String category) {
        logger.info("inside get menu item method");
        return menuLevel2.findElement(By.xpath(".//div[contains(@class, 'menu__link--header') and " +
                Util.xpathGetTextLower + " = " +
                "translate('" + category + "', 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz')]/.."));
    }

    public void close_subcategory_hamburger_menu() {    	
    	WebElement closeIcon = Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(menuLevel2.findElement(By.className("icon-close"))));        
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
        String categoryName = category.getText();
        category.click();
        Util.waitLoadingBar(driver);
        stateHolder.put("category", categoryName);
        logger.debug("'{}' category is clicked", categoryName);
    }

    public void click_random_subcategory() {
        String categorySelected = (String) stateHolder.get("category");        
                
        List<WebElement> menuItemLinks = getMenuItemElementForCategory(categorySelected).findElements(
                By.xpath(".//a[@class='menu__link menu__link--has-href' and not(text()='New Arrivals') and not(text()='Fall/Winter Weddings & Parties Lookbook') and contains(@href, '" + "/c/" + categorySelected.toLowerCase() + "')]"));
        
        WebElement subcategory = menuItemLinks.get(Util.randomIndex(menuItemLinks.size()));
        String subCategoryText = subcategory.getText();

        stateHolder.put("subcategory", subCategoryText);
        logger.debug("Selected subcategory is {} from {} category", subCategoryText, categorySelected);

        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(subcategory));
        subcategory.click();
    }

    public boolean isUserSignedIn() {
        Cookie user = driver.manage().getCookieNamed("user_id");

        return user != null;
    }
    
    public void clickSpecificSubcategory(String subCategoryText){    	
    	String categoryName = (String) stateHolder.get("category");    			
		click_on_subcategory(subCategoryText.toUpperCase(),categoryName);
		stateHolder.put("subcategory", subCategoryText);
    }
}
