package com.jcrew.page;

import com.jcrew.pojo.Product;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailPage {

    private final WebDriver driver;
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final Logger logger = LoggerFactory.getLogger(ProductDetailPage.class);

    @FindBy(id = "btn__add-to-bag")
    private WebElement addToBag;

    @FindBy(id = "btn__wishlist")
    private WebElement wishList;

    @FindBy(id = "c-product__variations")
    private WebElement productVariationSection;

    @FindBy(id = "c-product__sizes")
    private WebElement productSizesSection;

    @FindBy(className = "product__name")
    private WebElement productName;

    @FindBy(id = "c-product__quantity")
    private WebElement productQuantitySection;

    @FindBy(className = "primary-nav__item--bag")
    private WebElement bagContainer;

    @FindBy(id = "global__footer")
    private WebElement footer;

    @FindBy(className = "product__price--sale")
    private WebElement salePrice;

    @FindBy(id = "c-product__details")
    private WebElement productDetails;

    @FindBy(id = "c-product__overview")
    private WebElement productOverview;

    @FindBy(id = "c-product__actions")
    private WebElement productActionsSection;

    @FindBy(className = "message--headline")
    private WebElement messageHeadline;

    @FindBy(className = "message--body")
    private WebElement messageBody;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isProductDetailPage() {
        Util.waitForPageFullyLoaded(driver);
        return productName.isDisplayed() && StringUtils.isNotBlank(productName.getText());
    }

    public void select_variation() {
        List<WebElement> variations = productVariationSection.findElements(By.tagName("input"));

        if (!variations.isEmpty()) {
            WebElement variation = variations.get(0);

            if (variation.isSelected()) {
                logger.debug("Variation is already selected");
            } else {
                variation.click();
            }
        }
    }

    public void select_any_color() {
        try {
            Util.createWebDriverWait(driver).until(ExpectedConditions.presenceOfElementLocated(By.id("c-product__price-colors")));
            List<WebElement> colors = driver.findElement(By.id("c-product__price-colors")).findElements(By.className("colors-list__item"));
            int range = colors.size();
            int randomNumber = (int) (Math.random() * range);
            WebElement color = colors.get(randomNumber);

            Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(color));
            color.click();
        } catch (StaleElementReferenceException sere) {
            select_any_color();
        }
    }


    public void select_size() {
        List<WebElement> sizes = productSizesSection.findElements(By.className("sizes-list__item"));
        for (WebElement size : sizes) {
            if (!size.getAttribute("class").contains("is-unavailable")) {
                size.click();
                if (size.getAttribute("class").contains("is-selected")) {
                    logger.debug("Size has been selected {}", size.findElement(By.tagName("span")).getText());
                }
                break;
            }
        }
    }

    public void select_quantity(String quantity) {
        Select quantitySelect = getQuantitySelector();
        quantitySelect.selectByValue(quantity);

    }

    private Select getQuantitySelector() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productQuantitySection));
        WebElement quantitySelectWebElement = productQuantitySection.findElement(By.className("dropdown--quantity"));
        return new Select(quantitySelectWebElement);
    }

    public boolean isWishlistButtonPresent() {
        boolean result = false;
        if (!productActionsSection.findElements(By.id("btn__add-to-bag")).isEmpty()) {
            result = wishList.isDisplayed();
        }
        return result;
    }

    public void click_add_to_cart() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(addToBag));
        addToBag.click();
    }

    public int getNumberOfItemsInBag() {
        WebElement bagSize = bagContainer.findElement(By.className("js-cart-size"));
        String bagSizeStr = bagSize.getAttribute("innerHTML");
        String stringSize = bagSizeStr.replace("(", "").replace(")", "").trim();
        return Integer.parseInt(stringSize);
    }

    public boolean showsMinicartMessage(String message) {
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.textToBePresentInElementLocated(By.className("header__cart--details"), message));
        return true;
    }

    public String getSalePrice() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(salePrice));
        return salePrice.getText();
    }

    public void select_size(String productSize) throws InterruptedException {
        WebElement productSizeElement = getProductSizeElement(productSize);
        productSizeElement.click();        
    }

    private WebElement getProductSizeElement(String productSize) {

        return Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(productSizesSection.findElement(
                        By.xpath(".//li[@data-name='" + productSize + "']"))));
    }

    public void select_color(String productColor) {
        WebElement productColorElement = getProductColorElement(productColor);
        productColorElement.click();
    }

    private WebElement getProductColorElement(String productColor) {
        WebElement productColorElement = driver.findElement(By.id("c-product__price-colors")).findElement(
                By.xpath(".//li[@data-name='" + productColor + "']"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productColorElement));
        return productColorElement;
    }

    public String getSelectedColor() {
        WebElement productColorElement = driver.findElement(By.id("c-product__price-colors")).findElement(By.className("is-selected"));
        return productColorElement.getAttribute("data-name");
    }

    public String getSelectedSize() {
        WebElement productSizeElement = productSizesSection.findElement(By.className("is-selected"));
        return productSizeElement.getAttribute("data-name");
    }

    public String getBagButtonText() {
        return addToBag.getText();
    }

    public void click_update_cart() {
        addToBag.click();
    }

    public String getSelectedVariationName() {
        WebElement selectedElement = productVariationSection.findElement(By.className("is-selected"));
        WebElement productVariationNameElement = selectedElement.findElement(By.className("product__variation--name"));
        return productVariationNameElement.getText();
    }

    public String getSelectedQuantity() {
        Select quantitySelect = getQuantitySelector();
        String text = quantitySelect.getFirstSelectedOption().getText();
        if ("".equals(text)) {
            text = "1";
        }
        return text;
    }

    public String getWishlistButtonMessage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(wishList));
        return wishList.getText();
    }

    public void click_wishlist() {
        wishList.click();
    }

    public void go_to_wishlist() {
        WebElement wishlistConfirmation = Util.createWebDriverWait(driver).until(
                ExpectedConditions.presenceOfElementLocated(By.className("wishlist-confirmation-text")));
        wishlistConfirmation.findElement(By.tagName("a")).click();
    }

    public boolean isSizeSelectorSectionPresent() {
        return !productSizesSection.findElements(By.className("product__sizes")).isEmpty();
    }

    public boolean isColorSelectorSectionPresent() {
        return !driver.findElement(By.id("c-product__price-colors")).
                findElements(By.className("product__price-colors")).isEmpty();
    }

    public boolean isQuantitySelectorSectionPresent() {
        return !productQuantitySection.findElements(By.className("product__quantity")).isEmpty();
    }

    public boolean isAddToBagButtonPresent() {
        return !productActionsSection.findElements(By.id("btn__add-to-bag")).isEmpty();
    }

    public String getHeadlineMessage() {
        return messageHeadline.getText();
    }

    public String getBodyMessage() {
        return messageBody.getText();
    }

    public String getProductNameFromPDP() {
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(productOverview));
        String product_detail_name = productOverview.findElement(By.tagName("h1")).getText();
        return product_detail_name;
    }

    public String getProductPriceList() {
        String productListPrice;
        if (getVariationsNames().isEmpty()) {
            productListPrice = productDetails.findElement(By.className("product__price--list")).getText();
        } else {
            try {
                // running on mobile device
                productListPrice = productDetails.findElement(By.xpath("div/div/div[contains(@class, 'product__variation--wrap')" +
                        "/span[contains(@class, 'product__price--list')]]")).getAttribute("innerHTML");

            } catch (NoSuchElementException nsee) {
                // running in headless browser
                productListPrice = driver.findElement(By.id("c-product__variations")).findElement(By.className("is-selected")).
                        findElement(By.className("product__price--list")).getText();
            }
        }
        return productListPrice;
    }


    public boolean isPreOrderButtonDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(addToBag));
        return addToBag.isDisplayed();
    }

    public List<String> getVariationsNames() {
        List<WebElement> variationsList = productVariationSection.findElements(By.className("product__variation--name"));
        List<String> variationsListString = new ArrayList<>();
        for (WebElement variation : variationsList) {
            variationsListString.add(variation.getAttribute("innerHTML"));
        }
        return variationsListString;
    }

    public int getNumberOfColors() {
        return driver.findElement(By.id("c-product__price-colors")).
                findElements(By.className("colors-list__item")).size();
    }

    public String getProductPriceSale() {
        return productDetails.findElement(By.className("product__price--sale")).getText();
    }

    public String getProductPriceWas() {
        return productDetails.findElement(By.className("product__price--list")).getText();
    }

    public void select_random_variation() {
        try {
            List<WebElement> variationsList = driver.findElement(By.id("c-product__variations")).
                    findElements(By.className("product__variation"));

            if (!variationsList.isEmpty()) {
                int index = Util.randomIndex(variationsList.size());
                WebElement variation = variationsList.get(index);

                Product product = Util.getCurrentProduct();
                product.setSelectedVariation(variation.findElement(By.className("product__variation--name")).getText());

                variation.click();
            }
        } catch (StaleElementReferenceException sere) {
            select_random_variation();
        } catch (NoSuchElementException nsee) {
            logger.debug("No variations section was found for the product selected");
        }
    }

    public void select_random_color() {
        try {
            List<WebElement> colorsList = Util.createWebDriverWait(driver).until(
                    ExpectedConditions.visibilityOf(driver.findElement(By.id("c-product__price-colors")))).
                    findElements(By.className("colors-list__item"));

            if (!colorsList.isEmpty()) {
                int index = Util.randomIndex(colorsList.size());
                WebElement color = colorsList.get(index);
                Product product = Util.getCurrentProduct();
                product.setSelectedColor(color.getAttribute("data-name"));
                color.click();
                setSalePriceIfPresent(color, product);
            }

        } catch (StaleElementReferenceException sere) {
            select_random_color();
        }
    }

    private void setSalePriceIfPresent(WebElement color, Product product) {
        try {
            WebElement salePrice = driver.findElement(By.id("c-product__price-colors")).
                    findElement(By.cssSelector(".colors-list__item.is-selected")).
                    findElement(By.xpath("../../../span[contains(@class, 'product__price--sale')]"));
            product.setPriceSale(salePrice.getText());

        } catch (StaleElementReferenceException sere) {

            setSalePriceIfPresent(color, product);

        } catch (NoSuchElementException nsee) {
            logger.debug("There was no sale price for color: " + color.getAttribute("data-name"));
        }
    }

    public void select_random_size() {
        List<WebElement> allSizes = productSizesSection.findElements(By.className("sizes-list__item"));
        List<WebElement> selectableSizes = new ArrayList<>();
        for (WebElement size : allSizes) {
            if (!size.getAttribute("class").contains("is-unavailable")) {
                selectableSizes.add(size);
            }
        }

        if (!selectableSizes.isEmpty()) {
            int index = Util.randomIndex(selectableSizes.size());
            WebElement size = selectableSizes.get(index);
            Product product = Util.getCurrentProduct();
            product.setSelectedSize(size.getAttribute("data-name"));
            size.click();
        }
    }

    public String getButtonErrorMessage() {
        return productActionsSection.findElement(By.className("product__message")).getText();
    }

    public boolean isBagButtonText(String text) {
        return Util.createWebDriverWait(driver).until(
                ExpectedConditions.textToBePresentInElement(addToBag, text));
    }

    public boolean isWishlistConfirmationMessageDisplayed() {
        return productActionsSection.findElement(By.className("content-button-secondary-confirmation")).isDisplayed();
    }
}
