package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage {

    private final WebDriver driver;
    private Logger logger = LoggerFactory.getLogger(HomePage.class);


    @FindBy(id = "lightbox")
    private WebElement modalWindow;

    @FindBy(className = "closePopup")
    private WebElement closePopupLink;


    @FindBy(className = "header__search__input")
    private WebElement searchInput;

    @FindBy(className = "header__search__button--find")
    private WebElement headerSearchButtonFind;

    @FindBy(className = "c-email-capture")
    private WebElement emailCaptureSection;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void hit_enter_in_search_field() {
        searchInput.sendKeys(Keys.ENTER);
    }

    public void input_search_term(String searchTerm) {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(searchTerm);
    }

    public void click_on_search_button_for_input_field() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerSearchButtonFind));
        headerSearchButtonFind.click();
        Util.waitLoadingBar(driver);
    }

    public boolean isHomePage() {
        final WebElement pageHome = Util.createWebDriverWait(driver).until(
                ExpectedConditions.presenceOfElementLocated(By.id("page__home")));
        return pageHome.isDisplayed();
    }

    public void close_email_pop_up() {
        emailCaptureSection.findElement(By.className("js-email-capture--close")).click();
    }

    public boolean isEmailPopUpNotDisplayed() {
        try {
            return !(emailCaptureSection.isDisplayed());
        } catch (NoSuchElementException ne) {
            logger.debug("Email capture pop up is not present");
            return true;
        }
    }

    public boolean isEmailPopUpDisplayed() {
        try {
            return emailCaptureSection.isDisplayed();
        } catch (NoSuchElementException ne) {
            logger.debug("Email capture pop up is not present");
            return false;
        }
    }

    public void enter_email_address() {
        emailCaptureSection.findElement(By.className("js-email-capture--input")).sendKeys("test@example.org");
    }

    public void click_on_the_arrow_button_to_submit(){
        emailCaptureSection.findElement(By.className("js-email-capture--button")).click();
    }
}

