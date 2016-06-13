package com.jcrew.page;

import com.google.common.base.Predicate;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class HeaderWrap {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(HeaderWrap.class);
    private final WebDriverWait wait;
    private final Actions hoverAction;

    @FindBy(xpath = "//li[@class='primary-nav__item primary-nav__item--menu']/a")
    private WebElement menu;
    @FindBy(xpath = "//li[@class='primary-nav__item primary-nav__item--search']/a")
    private WebElement search;
    @FindBy(xpath = "//li[@class='primary-nav__item primary-nav__item--stores']/a")
    private WebElement stores;
    @FindBy(id = "c-header__userpanel")
    private WebElement sign_in;
    @FindBy(id = "c-header__userpanelrecognized")
    private WebElement myAccount;
    @FindBy(id = "js-header__cart")
    private WebElement bag;
    @FindBy(id = "c-header__userpanel")
    private WebElement userPanel;
    @FindBy(id = "global__promo")
    private WebElement global_promo;
    @FindBy(id = "c-header__minibag")
    private WebElement minibag;
    @FindBy(id = "global__header")
    private WebElement global_header;
    @FindBy(id = "global__nav")
    private WebElement global_nav;
    @FindBy(id = "js-header__logo")
    private WebElement header_logo;
    @FindBy(className = "header__department-nav")
    private WebElement top_nav;

    private WebElement dropdown;
    
    @FindBy(xpath=".//div[@id='c-header__factory-link']/a")
    private WebElement lookingForFactoryLinkInHeader;

    public HeaderWrap(WebDriver driver) {
        this.driver = driver;
        this.hoverAction = new Actions(driver);
        this.wait = Util.createWebDriverWait(driver);
        reload();
    }

    public void reload() {
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(global_promo));
    }

    public void openMenu() {
        menu.click();
        wait.until(ExpectedConditions.visibilityOf(global_nav));
    }

    public void clickLogo() {
        if (header_logo.isDisplayed()) {
            header_logo.click();
        } else {
            clickBreadCrumb("J.Crew");
        }
    }

    public void clickBreadCrumb(String text) {
        WebElement breadCrumb = global_header.findElement(
                By.xpath(".//a[@class='breadcrumb__link' and text()='" + text + "']"));
        breadCrumb.click();
    }

    public void searchFor(String searchItem) {
        search.click();

        wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(minibag)));
        WebElement searchHeader = global_header.findElement(By.className("js-c-header__search"));
        WebElement searchInput = searchHeader.findElement(By.className("js-header__search__input"));
        WebElement searchButton = searchHeader.findElement(By.className("js-header__search__button--find"));

        PropertyReader propertyReader = PropertyReader.getPropertyReader();
        String env = propertyReader.getProperty("environment");
        TestDataReader testdataReader = TestDataReader.getTestDataReader();

        if(testdataReader.hasProperty(env+ "." + searchItem)){
            searchItem = testdataReader.getData(env + "." + searchItem);
        }

        searchInput.sendKeys(searchItem);
        searchButton.click();
        logger.info("Searching for {}", searchItem);
        Util.waitLoadingBar(driver);
    }

    public void searchForSpecificTerm(String searchTerm) {
        search.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(minibag)));
        WebElement searchHeader = global_header.findElement(By.className("js-c-header__search"));
        WebElement searchInput = searchHeader.findElement(By.className("js-header__search__input"));
        WebElement searchButton = searchHeader.findElement(By.className("js-header__search__button--find"));
        searchInput.sendKeys(searchTerm);
        searchButton.click();
        logger.info("Searching for {}", searchTerm);
        Util.waitLoadingBar(driver);

    }

    public void clickSignIn() {
        wait.until(ExpectedConditions.visibilityOf(sign_in));
        WebElement signInLink = sign_in.findElement(By.tagName("a"));
        signInLink.click();
    }

    public void clickBag() {
        bag.click();
    }

    public boolean bagHasProducts() {
        WebElement bagContainer = bag.findElement(By.xpath(".//parent::li"));
        String classString = bagContainer.getAttribute("class");
        return classString.contains("primary-nav__item--bag-filled");
    }

    public void hoverOverIcon(String icon) {

        if ("bag".equalsIgnoreCase(icon)) {

            PropertyReader propertyReader = PropertyReader.getPropertyReader();
            String browser = propertyReader.getProperty("browser");

            if ("chrome".equals(browser) || "firefox".equals(browser)) {
                hoverAction.moveToElement(bag);
                hoverAction.perform();
            } else {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript(
                        "jcrew.jQuery('.primary-nav__item--bag-filled').trigger('mouseenter');");
            }
            wait.until(ExpectedConditions.visibilityOf(minibag));

        } else if ("my account".equalsIgnoreCase(icon)) {

            wait.until(ExpectedConditions.visibilityOf(myAccount));
            hoverAction.moveToElement(myAccount);
            hoverAction.perform();

        } else if ("logo".equalsIgnoreCase(icon)) {

            WebElement logo = global_header.findElement(By.className("c-header__logo"));
            String logoClass = logo.getAttribute("class");

            if (logoClass.contains("is-hidden")) {
                logo = global_header.findElement(By.className("c-header__breadcrumb"));
            }

            hoverAction.moveToElement(logo);
            hoverAction.perform();

        }
    }

    public String getWelcomeMessage() {
        dropdown = userPanel.findElement(By.tagName("dl"));
        WebElement welcomeRow = dropdown.findElement(By.xpath(".//dd[@class='c-nav__userpanel--welcomeuser']"));
        return welcomeRow.getText();
    }

    public void goToMyDetailsDropDownMenu(String option) {
        hoverOverIcon("my account");
        dropdown = userPanel.findElement(By.tagName("dl"));
        WebElement optionElement = dropdown.findElement(By.linkText(option));
        optionElement.click();
    }

    public boolean isSignInVisible() {
        boolean result = false;
        wait.until(ExpectedConditions.visibilityOf(sign_in));
        List<WebElement> signInLink = sign_in.findElements(By.tagName("a"));

        if (signInLink.size() == 1)
            result = true;

        return result;
    }

    public void waitUntilNoCheckOutDropdown() {
        List<WebElement> checkoutDropdown = global_header.findElements(By.className("js-header__cart"));

        if (checkoutDropdown.size() > 0) {
            wait.until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver driver) {
                    List<WebElement> headerCart = global_header.findElements(By.className("js-header__cart"));

                    return headerCart.size() == 0;
                }
            });
        }
    }

    public void clickDeptLinkFromTopNav(String dept) {
        String url = driver.getCurrentUrl();
        top_nav.findElement(By.xpath("//span[contains(@class, 'department-nav__text') and "
                + Util.xpathGetTextLower + " = '" + dept.toLowerCase() + "']")).click();

        if(!"view all".equals(dept))
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
    }

    public List<String> getTopNavOptions() {
        List<WebElement> options = top_nav.findElements(By.className("department-nav__item"));
        List<String> optionsString = new ArrayList<>(options.size());

        for(WebElement option : options) {
            optionsString.add(option.getText().toLowerCase());
        }

        return optionsString;
    }
}
