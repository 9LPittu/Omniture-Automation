package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("js-footer__fullsite__link")));
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
        Util.waitWithStaleRetry(driver,pageHome);
        return pageHome.isDisplayed();
    }

    public void close_email_pop_up() {
        emailCaptureSection.findElement(By.className("js-email-capture--close")).click();
    }

    public void handle_email_pop_up() {

        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        boolean emailCapture = jse.executeScript("return jcrew.config.showEmailCapture;").equals(true);
        logger.debug("Email capture? {}", emailCapture);

        if(emailCapture) {
            logger.debug("Email capture on, let's turn it off!!");

            List<WebElement> email_capture = driver.findElements(
                    By.xpath("//div[@id='global__email-capture']/section/div[@class = 'email-capture--close js-email-capture--close']"));

            if(email_capture.size() > 0) {
                WebElement close = email_capture.get(0);
                Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(close));
                close.click();
            } else {
                logger.debug("No email capture displayed...");
            }

        }
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
        try {
            emailCaptureSection.findElement(By.className("js-email-capture--input")).sendKeys("test@example.org");
        }catch (NoSuchElementException e) {
            logger.debug("Email capture pop up is not present");
        }
    }

    public void click_on_the_arrow_button_to_submit(){
        try {
            emailCaptureSection.findElement(By.className("js-email-capture--button")).click();
        }catch (NoSuchElementException e) {
            logger.debug("Email capture pop up is not present");
        }

    }
}

