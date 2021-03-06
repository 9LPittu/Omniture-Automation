package com.jcrew.page;

import com.google.common.base.Function;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HamburgerMenu {

    private final StateHolder stateHolder = StateHolder.getInstance();
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(HamburgerMenu.class);
    TestDataReader testDataReader = TestDataReader.getTestDataReader();

    @FindBy(className = "header__primary-nav__wrap")
    private WebElement hamburgerMenuLink;

    @FindBy(xpath = ".//a[contains(@class,'js-primary-nav__link--menu')]")
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

    @FindBy(xpath = "//div[@class='menu__item--userpanel']")
    private WebElement userPanelInHamburgerMenu;

    public HamburgerMenu(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void click_on_hamburger_menu() {
        WebDriverWait wait = Util.createWebDriverWait(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("footer__country-context")));
        Util.waitWithStaleRetry(driver, hamburgerMenu);

        int attempts = 0;
        while (attempts <= 2) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(hamburgerMenu));
                Util.clickWithStaleRetry(hamburgerMenu);
                Util.createWebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//nav[@id='global__nav']")));
                break;
            } catch (Exception e) {
                attempts++;
            }
        }

        if (attempts >= 2) {
            throw new NoSuchElementException("Failed to click on hamburger menu");
        }

    }

    public boolean isHamburgerMenuPresent() {
        boolean isIconDisplayed = hamburgerMenuLink.findElement(By.className("icon-header-menu")).isDisplayed();
        boolean isMenuTextDisplayed = hamburgerMenuLink.findElement(By.className("primary-nav__text--menu")).isDisplayed();
        return isIconDisplayed && isMenuTextDisplayed;
    }

    public void click_on_sign_in_link_from_hamburger_menu() {
        try {
            Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(signInLinkFromHamburger));
            signInLinkFromHamburger.click();
        } catch (TimeoutException timeout) {
            logger.debug("Timeout while waiting for sign in button in hamburger menu");
            Logs errorlog = driver.manage().logs();
            LogEntries errors = errorlog.get(LogType.BROWSER);

            for (LogEntry error : errors) {
                logger.error("Browser logs {}", error.getMessage());
            }

        }
    }

    public void click_on_sign_in_link() {
        click_on_hamburger_menu();
        click_on_sign_in_link_from_hamburger_menu();
    }

    public void click_on_back_link() {
        WebElement nav__wrap = globalNav.findElement(By.className("nav__wrap"));

        String nav__wrap__class = nav__wrap.getAttribute("class");

        if (nav__wrap__class.contains("is-offscreen-left-x1")) {
            WebElement js_menus__level2 = nav__wrap.findElement(By.className("js-menus--level2"));
            WebElement back = js_menus__level2.findElement(By.className("js-menu__btn--back"));

            back.click();
        } else if (nav__wrap__class.contains("is-offscreen-left-x2")) {
            WebElement js_menus__level3 = nav__wrap.findElement(By.className("js-menus--level3"));
            WebElement back = js_menus__level3.findElement(By.className("js-menu__btn--back"));

            back.click();
        }
    }

    public boolean isCategoryPresent(String category) {
    	try{
    		return getCategory(category).isDisplayed();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }

    public void click_on_category(String category) {
        String url = driver.getCurrentUrl();

        getCategory(category).click();

        if ("sale".equalsIgnoreCase(category)) {
            Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));        
        }
        
        Util.waitLoadingBar(driver);

        logger.debug("'{}' category is clicked", category);
        stateHolder.put("category", category);
    }

    private WebElement getCategory(String category) {
        WebElement element = categoryMenu.findElement(By.xpath(".//a[" + Util.xpathGetTextLower + "='" + category.toLowerCase() + "']"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    public void click_on_subcategory(String subcategory, String category) {
        WebElement subCategoryElement = getSubcategoryFromMenu(subcategory, category);
        Util.scrollAndClick(driver, subCategoryElement);
        logger.info("Actual subcategory clicked: {}", subcategory);
        stateHolder.put("subcategory", subcategory);
        Util.waitLoadingBar(driver);
    }

    public void click_on_selected_featured_this_month(String choice) {
    	WebElement level3Menus = Util.createWebDriverWait(driver).until(new Function<WebDriver, WebElement>(){
			@Override
			public WebElement apply(WebDriver driver) {
				WebElement level3Menus = driver.findElement(
		                By.xpath("//div[@class='c-menus menus--level3 js-menus--level3']/div[@class='menu__item is-lazy-loaded']"));
				try{
					if(level3Menus.isDisplayed()){
						return level3Menus;
					}
					return null;
				}
				catch(NoSuchElementException nsee){
					return null;
				}
			}
        });
        
        WebElement looksWeLove = level3Menus.findElement(
                By.xpath(".//span[@class='menu__link__label' and contains(" + Util.xpathGetTextLower + ",'" + choice.toLowerCase() + "')]"));

        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(looksWeLove));
        looksWeLove.click();
        Util.waitLoadingBar(driver);
    }

    public void click_on_sale_subcategory(String subcategory) {
    	subcategory = subcategory.trim();
        Util.waitLoadingBar(driver);
        
        int i = 0;
        while(i<=2){
        	try{
        		getSubcategoryFromSale(subcategory, Util.getDefaultTimeOutValue()/3).click();
        		Util.createWebDriverWait(driver,Util.getDefaultTimeOutValue()/3).until(ExpectedConditions.urlContains("search"));
                stateHolder.put("sale category", subcategory);
                break;
        	}
        	catch(Exception e){
        		i++;
        		if(i>2){
        			e.printStackTrace();
        		}
        	}
        }
        
        Util.waitLoadingBar(driver);
    }

    private WebElement getSubcategoryFromMenu(String subcategory, String category) {
        WebElement categories = getMenuItemElementForCategory(category);

        Util.createWebDriverWait(driver).until(ExpectedConditions.textToBePresentInElement(categories, subcategory));

        WebElement categoryLink = categories.findElement(By.linkText(subcategory));

        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(categoryLink));
        return categoryLink;
    }

    private WebElement getSubcategoryFromSale(String subcategory, int timeOut) {
        Util.createWebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOf(saleCategoryList));
        
        WebElement subCategoryElementFromSale = Util.createWebDriverWait(driver, timeOut).until(
        		  ExpectedConditions.visibilityOf(saleCategoryList.findElement(
        		  By.xpath(".//div[@class='c-category__header accordian__header' and " +
                  Util.xpathGetTextLower + " = " +
                  "translate('" + subcategory + "', 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz')]/.."))));
        
        Util.createWebDriverWait(driver, timeOut).until(ExpectedConditions.elementToBeClickable(subCategoryElementFromSale));
        
        return subCategoryElementFromSale;
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
        Util.scrollAndClick(driver, subcategory);        
        Util.waitLoadingBar(driver);
    }

    public boolean isUserSignedIn() {
        Cookie user = driver.manage().getCookieNamed("user_id");

        return user != null;
    }

    public void clickSpecificSubcategory(String subCategoryText) {
        String categoryName = (String) stateHolder.get("category");
        click_on_subcategory(subCategoryText.toUpperCase(), categoryName);
        stateHolder.put("subcategory", subCategoryText);
    }
    
    public boolean isUserPanelLinkPresent(String linkName){
    	Util.waitForPageFullyLoaded(driver);
    	Util.waitLoadingBar(driver);
		WebElement userPanelElement = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='menu__item--userpanel']")));
		WebElement linkElement = Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(
								 userPanelElement.findElement(By.xpath(
										 	".//a[contains(@class,'menu__link--userpanel') and text()='" + linkName.toUpperCase() + "']"))));
		return linkElement.isDisplayed();
    }
}