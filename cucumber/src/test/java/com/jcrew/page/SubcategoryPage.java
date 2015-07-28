package com.jcrew.page;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @FindAll(value = {
            @FindBy(xpath = ".//div[@class='c-product-tile']")
    })
    List<WebElement> productsFromGrid;

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

    public void hover_first_product_in_grid() {
        Actions action = new Actions(driver);
        WebElement firstProductFromGrid = productsFromGrid.get(0);
        action.moveToElement(firstProductFromGrid);
        action.perform();
    }

    private boolean isPriceAndNameValidFor(WebElement product) {
        boolean result = true;
        String productName = null;
        String productPrice = null;

        try {

            productName = product.findElement(By.className("tile__detail--name")).getText();
            productPrice = product.findElement(By.className("tile__detail--price--list")).getText();

        } catch (NoSuchElementException nsee) {
            try {

                productPrice = product.findElement(By.className("tile__detail--price--was")).getText() + " "
                        + product.findElement(By.className("tile__detail--price--sale")).getText();

            } catch (NoSuchElementException nse) {
                result = false;
            }
        }

        logger.debug("Price found for product with name {} is {}", productName, productPrice);

        if (StringUtils.isBlank(productPrice) || StringUtils.isBlank(productName)) {
            result = false;
        }

        return result;
    }

    public boolean isFirstProductNameAndPriceValid() {
        WebElement product = productsFromGrid.get(0);
        return isPriceAndNameValidFor(product);
    }

    public boolean areFirstProductVariationsValid() {
        WebElement firstProductFromGrid = productsFromGrid.get(0);
        boolean result = false;
        try {
            String productCount = firstProductFromGrid.findElement(By.className("tile__detail--colors-count")).getText();
            Pattern p = Pattern.compile("available in (\\d)+ colors");
            Matcher matcher = p.matcher(productCount);
            int numberOfVariationsInText = 0;

            if (matcher.matches()) {

                String numberOfVariationsString = matcher.group(1);

                logger.debug("color is {} ", numberOfVariationsString);

                numberOfVariationsInText = Integer.parseInt(numberOfVariationsString);

                int numberOfVariationsDisplayed = Integer.parseInt(firstProductFromGrid.
                        findElement(By.className("colors__wrap")).
                        getAttribute("data-colors-count"));

                result = numberOfVariationsInText == numberOfVariationsDisplayed;

            }


        } catch (NoSuchElementException nsee) {

            logger.debug("Color count not found for product with name {}, there should not be variations",
                    firstProductFromGrid.findElement(By.className("tile__detail--name")).getText());

            result = firstProductFromGrid.findElements(By.className("colors-list__item")).isEmpty();
        }

        return result;

    }
}
