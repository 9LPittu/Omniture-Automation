package com.jcrew.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

    @FindBy(id = "global__main")
    private WebElement globalMain;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void hit_enter_in_search_field() {
        searchInput.sendKeys(Keys.ENTER);
    }

    public void input_search_term(String searchTerm) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(searchTerm);
    }
    public void input_yellow_dresses() throws InterruptedException  {
        searchInput.clear();
        searchInput.sendKeys("yellow dresses ");
        
        
    }

    public void click_on_search_button_for_input_field() {
        headerSearchButtonFind.click();
    }

    public void click_on_women_pdp_hamburger_menu() {
        WebElement pdpmenu = driver.findElement(By.xpath("//*[@id='global__header']/div[2]/section/div[1]/div/div/ul[1]/li[1]/a"));
        logger.info("verify menu from women pdp{}", pdpmenu.getText());
        pdpmenu.click();
    }

    public boolean isHomePage() {
        return globalMain.isDisplayed();
    }
}
