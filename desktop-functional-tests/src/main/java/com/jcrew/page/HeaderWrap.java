package com.jcrew.page;

import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class HeaderWrap {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(HeaderWrap.class);
    private final WebDriverWait wait;
    private final Actions hoverAction;

    @FindBy (xpath = "//li[@class='primary-nav__item primary-nav__item--menu']/a")
    private WebElement menu;
    @FindBy (xpath = "//li[@class='primary-nav__item primary-nav__item--search']/a")
    private WebElement search;
    @FindBy (xpath = "//li[@class='primary-nav__item primary-nav__item--stores']/a")
    private WebElement stores;
    @FindBy (id = "js-header__logo")
    private WebElement logo;
    @FindBy (id = "c-header__userpanel")
    private WebElement sign_in;
    @FindBy (id = "c-header__userpanelrecognized")
    private WebElement myAccount;
    @FindBy (id = "js-header__cart")
    private WebElement bag;
    @FindBy (id = "c-nav__userpanel")
    private WebElement userPanel;
    @FindBy (id = "global__promo")
    private WebElement global_promo;

    private WebElement dropdown;

    public HeaderWrap(WebDriver driver){
        this.driver = driver;
        this.hoverAction = new Actions(driver);
        this.wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver,this);
        wait.until(ExpectedConditions.visibilityOf(global_promo));
    }

    public void reload(){
        PageFactory.initElements(driver,this);
    }

    public void openMenu(){

    }

    public void searchFor(String searchTerm){

    }

    public void clickStores(){

    }

    public void clickSignIn(){
        WebElement signInLink = sign_in.findElement(By.tagName("a"));
        signInLink.click();
    }

    public void hoverBag(){

    }

    public void clickBag(){

    }

    public void hoverMyAccount(){
        wait.until(ExpectedConditions.visibilityOf(myAccount));
        hoverAction.moveToElement(myAccount);
        hoverAction.perform();
    }

    public String getWelcomeMessage() {
        dropdown = userPanel.findElement(By.tagName("dl"));
        WebElement welcomeRow = dropdown.findElement(By.xpath(".//dd[@class='c-nav__userpanel--welcomeuser']"));
        return welcomeRow.getText();
    }

    public void goToDropDownMenu(String option) {
        hoverMyAccount();
        logger.debug("userPanel = {}", userPanel.getAttribute("class"));
        dropdown = userPanel.findElement(By.tagName("dl"));
        WebElement optionElement = dropdown.findElement(By.linkText(option));
        optionElement.click();
    }

    public boolean isSignInVisible() {
        return sign_in.isDisplayed();
    }
}
