package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubcategoryPage {


    private final WebDriver driver;

    @FindBy(css = "button.get-quickshop")
    WebElement quickShopButton;

    @FindBy(xpath = ".//*[@id='sizes1']/div[not(contains(@class, 'unavailable'))][1]")
    WebElement availableSize;

    @FindBy(className = "add-item")
    WebElement addItem;

    @FindBy(className = "quickshop-close")
    WebElement quickShopCloseButton;

    @FindBy(id = "globalHeaderShoppingBagBttn2")
    WebElement shoppingBagLink;

    @FindBy(id = "qsLightBox")
    WebElement quickShopModal;

    @FindBy(className = "product__grid")
    WebElement productGrid;

    private Logger logger = LoggerFactory.getLogger(SubcategoryPage.class);

    public SubcategoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void adds_a_product_to_shopping_bag() throws Throwable {

        quickShopButton.click();

        availableSize.click();

        addItem.click();

        quickShopCloseButton.click();

        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(
                    By.id("qsLightBox")));

        } catch (NoSuchElementException nsee) {
            logger.info("Modal element is not present, this is expected to happen");
        }

    }

    public void clicks_on_shopping_bag_link() throws Throwable {

        String subcategoryUrl = driver.getCurrentUrl();
        new WebDriverWait(driver, 10).
                until(ExpectedConditions.visibilityOf(shoppingBagLink));

        shoppingBagLink.click();

        if (subcategoryUrl.equals(driver.getCurrentUrl())) {
            shoppingBagLink.click();
        }

        if (subcategoryUrl.equals(driver.getCurrentUrl())) {
            //even though the element was clicked we did not get redirected
            //this seems to happen in PhantonJS and we need to get directly to the requested page.

            String href = shoppingBagLink.getAttribute("href");

            logger.info("click did not work, we may be using phantomjs and the link for shopping link bag does not " +
                    "work as expected for this browser, redirecting to shopping bag page {}", href);

            driver.get(href);

        }

    }

    public boolean quickShopModalIsPresent() {
        boolean isPresent;
        try {
            isPresent = quickShopModal.isDisplayed();
        } catch (NoSuchElementException nsee) {
            isPresent = false;
        }
        return isPresent;
    }

    public boolean isProductGridPresent() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(productGrid));
        return productGrid.isDisplayed();
    }
}
