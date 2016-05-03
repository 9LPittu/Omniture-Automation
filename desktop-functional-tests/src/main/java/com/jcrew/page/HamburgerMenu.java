package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HamburgerMenu {

	private final StateHolder stateHolder = StateHolder.getInstance();
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(HamburgerMenu.class);
    TestDataReader testDataReader = TestDataReader.getTestDataReader();
    
    @FindBy(className = "js-primary-nav__link--menu")
    private WebElement hamburgerMenu;
    
    @FindBy(id = "global__nav")
    private WebElement globalNav;
    
    @FindBy(className = "c-menu")
    private WebElement categoryMenu;
    
    @FindBy(className = "menus--level1")
    private WebElement menuLevel1;
    
    @FindBy(className = "menus--level2")
    private WebElement menuLevel2;
    
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
    
    public void click_random_category() {
        String categories = testDataReader.getData("categories");
        String categoriesArray[] = categories.split(";");

        int index = Util.randomIndex(categoriesArray.length);
        WebElement category = getCategory(categoriesArray[index]);

        String categoryName = category.getText();
        category.click();
        Util.waitLoadingBar(driver);

        stateHolder.put("category", categoryName);
        logger.debug("'{}' category is clicked", categoryName);
    }
    
    public void clickSpecificSubcategory(String subCategoryText){
    	String categoryName = (String) stateHolder.get("category");
		click_on_subcategory(subCategoryText.toUpperCase(),categoryName);
		stateHolder.put("subcategory", subCategoryText);
    }
    
    public void click_on_subcategory(String subcategory, String category) {
    	String currentURL = driver.getCurrentUrl();
        getSubcategoryFromMenu(subcategory, category).click();
        logger.info("Actual subcategory clicked: {}", subcategory);
        stateHolder.put("subcategory", subcategory);
        Util.waitLoadingBar(driver);
    }
    
    private WebElement getSubcategoryFromMenu(String subcategory, String category) {
        WebElement categories = getMenuItemElementForCategory(category);

        Util.createWebDriverWait(driver).until(ExpectedConditions.textToBePresentInElement(categories,subcategory));

        WebElement categoryLink = categories.findElement(By.linkText(subcategory));

        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(categoryLink));
        return categoryLink;
    }
    
    private WebElement getMenuItemElementForCategory(String category) {
        logger.info("inside get menu item method");
        return menuLevel2.findElement(By.xpath(".//div[contains(@class, 'menu__link--header') and " +
                Util.xpathGetTextLower + " = " +
                "translate('" + category + "', 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz')]/.."));
    }
    
    public void click_random_subcategory() {

        String categorySelected = (String) stateHolder.get("category");        
                
        categorySelected = categorySelected.toLowerCase();

        String subCategories = testDataReader.getData(categorySelected);
        String subCategoriesArray[] = subCategories.split(";");

        int index = Util.randomIndex(subCategoriesArray.length);
        String subCatSelected = subCategoriesArray[index];

        List<WebElement> menuItemLinks = getMenuItemElementForCategory(categorySelected).findElements(
                By.xpath(".//a[@class='menu__link menu__link--has-href' and " +
                                               "contains(@name,'" + categorySelected + ">" + subCatSelected + "')]"));
        
        WebElement subcategory = menuItemLinks.get(Util.randomIndex(menuItemLinks.size()));
        String subCategoryText = subcategory.getText();

        stateHolder.put("subcategory", subCategoryText);
        logger.debug("Selected subcategory is {} from {} category", subCategoryText, categorySelected);

        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(subcategory));
        subcategory.click();
        Util.waitLoadingBar(driver);
    }
    
    public boolean isCategoryPresent(String category) {
        return getCategory(category).isDisplayed();
    }
    
    public void close_hamburger_menu() {
        WebElement iconClose = menuLevel1.findElement(By.className("icon-close"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(iconClose));
        iconClose.click();
    }
}