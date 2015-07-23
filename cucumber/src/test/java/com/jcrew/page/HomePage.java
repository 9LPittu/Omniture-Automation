package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage {

    private final WebDriver driver;
    private Logger logger = LoggerFactory.getLogger(HomePage.class);

    @FindBy(xpath = ".//*[@id='globalHeaderNav']/li[1]/a")
    private WebElement firstCategoryDisplayed;

    @FindBy(id = "lightbox")
    private WebElement modalWindow;

    @FindBy(className = "closePopup")
    private WebElement closePopupLink;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selects_a_category() {
        closeModalWindowIfPresent();

        new WebDriverWait(driver, 10).
                until(ExpectedConditions.elementToBeClickable(firstCategoryDisplayed));

        firstCategoryDisplayed.click();

    }


    private void closeModalWindowIfPresent() {
        try {

            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(modalWindow));

            closePopupLink.click();

            new WebDriverWait(driver, 10).until(ExpectedConditions.not(
                    ExpectedConditions.visibilityOf(modalWindow)));

        } catch (WebDriverException wde) {
            logger.info("Initial window did not show up, continuing with tests");
        }
    }
}
