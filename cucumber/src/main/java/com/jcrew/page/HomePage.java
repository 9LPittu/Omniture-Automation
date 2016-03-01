package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
}
