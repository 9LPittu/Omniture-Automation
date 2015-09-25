package com.jcrew.page;

import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailPage {

    private final WebDriver driver;
    private final StateHolder stateHolder = StateHolder.getInstance();

    private Logger logger = LoggerFactory.getLogger(ProductDetailPage.class);

    @FindBy(id = "btn__add-to-bag")
    private WebElement addToBag;

    @FindBy(id = "btn__wishlist")
    private WebElement wishList;

    @FindBy(id = "c-product__variations")
    private WebElement productVariationSection;

    @FindBy(id = "c-product__price-colors")
    private WebElement productColorsSection;

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

    @FindBy(id = "c-product__variations")
    private WebElement productVariations;


    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public boolean isProductDetailPage() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(productName));
        boolean isProductDetailPage = productName.isDisplayed() && StringUtils.isNotBlank(productName.getText());
        return isProductDetailPage && footer.isDisplayed();
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

    public void select_color() {
        try {
            List<WebElement> colors = productColorsSection.findElements(By.className("colors-list__item"));
            WebElement color = colors.get(0);
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(color));
            color.click();
        } catch (StaleElementReferenceException sele) {
            select_color();
        }

    }

    public void select_any_color() {

        List<WebElement> colors = productColorsSection.findElements(By.className("colors-list__item"));
        int min = 0;
        int max = colors.size() - 1;
        logger.info("no of products {}", max);

        int range = (max - min) + 1;
        int randomNumber = (int) (Math.random() * range) + min;
        WebElement color = colors.get(randomNumber);
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(color));
        color.click();

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
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(productQuantitySection));
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
        addToBag.click();
    }

    public int getNumberOfItemsInBag() {
        WebElement bagSize = bagContainer.findElement(By.className("js-cart-size"));
        String bagSizeStr = bagSize.getAttribute("innerHTML");
        logger.debug("Bag Size is {}", bagSizeStr);
        String stringSize = bagSizeStr.replace("(", "").replace(")", "").trim();
        return Integer.parseInt(stringSize);
    }

    public String getMinicartMessage() {
        final WebElement miniCart = new WebDriverWait(driver, 60).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".header__cart--details > p")));
        final String confirmationMessage = miniCart.getAttribute("innerHTML");
        logger.debug("Confirmation message is {} ", confirmationMessage);
        return confirmationMessage;
    }

    public String getSalePrice() {
        return salePrice.getText();
    }

    public void select_size(String productSize) {
        WebElement productSizeElement = getProductSizeElement(productSize);

        productSizeElement.click();
    }

    private WebElement getProductSizeElement(String productSize) {
        return productSizesSection.findElement(
                By.xpath(".//li[@data-name='" + productSize + "']"));
    }

    public void select_color(String productColor) {
        WebElement productColorElement = getProductColorElement(productColor);
        productColorElement.click();
    }

    private WebElement getProductColorElement(String productColor) {
        WebElement productColorElement = productColorsSection.findElement(
                By.xpath(".//li[@data-name='" + productColor + "']"));
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(productColorElement));
        return productColorElement;
    }

    public String getSelectedColor() {
        WebElement productColorElement = productColorsSection.findElement(By.className("is-selected"));
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
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(wishList));
        return wishList.getText();
    }

    public void click_wishlist() {
        wishList.click();
    }

    public void go_to_wishlist() {
        WebElement wishlistConfirmation = new WebDriverWait(driver, 10).until(
                ExpectedConditions.presenceOfElementLocated(By.className("wishlist-confirmation-text")));
        wishlistConfirmation.findElement(By.tagName("a")).click();
    }

    public String getWishlistConfirmationMessage() {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.
                presenceOfElementLocated(By.className("content-button-secondary-confirmation"))).getText();
    }

    public boolean isSizeSelectorSectionPresent() {
        return !productSizesSection.findElements(By.className("product__sizes")).isEmpty();
    }

    public boolean isColorSelectorSectionPresent() {
        return !productColorsSection.findElements(By.className("product__price-colors")).isEmpty();
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
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.visibilityOf(productOverview));
        String product_detail_name = productOverview.findElement(By.tagName("h1")).getText();
        logger.info(product_detail_name);
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
                productListPrice = productVariations.findElement(By.className("is-selected")).
                        findElement(By.className("product__price--list")).getText();
            }
        }
        return productListPrice;
    }


    public boolean isPreOrderButtonDisplayed() {
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
        return productColorsSection.findElements(By.className("colors-list__item")).size();
    }

    public String getProductPriceSale() {
        String productDetailSalePrice = productDetails.findElement(By.className("product__price--sale")).getText();
        return productDetailSalePrice;
    }

    public String getProductPriceWas() {
        String productDetailWasPrice = productDetails.findElement(By.className("product__price--list")).getText();
        return productDetailWasPrice;
    }

    public void select_random_variation() {
        List<WebElement> variationsList = productVariations.findElements(By.className("product__variation"));
        if (!variationsList.isEmpty()) {
            int index = Util.randomIndex(variationsList.size());
            WebElement variation = variationsList.get(index);
            stateHolder.put("variation", variation.findElement(By.className("product__variation--name")).getText());
            variation.click();
        }
    }

    public void select_random_color() {
        List<WebElement> colorsList = productColorsSection.findElements(By.className("colors-list__item"));
        if (!colorsList.isEmpty()) {
            int index = Util.randomIndex(colorsList.size());
            WebElement color = colorsList.get(index);
            stateHolder.put("color", color.getAttribute("data-name"));
            color.click();
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
            stateHolder.put("size", size.getAttribute("data-name"));
            size.click();
        }
    }

    public String getButtonErrorMessage() {
        return productActionsSection.findElement(By.className("product__message")).getText();
    }

}