package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ProductDetails {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(ProductDetails.class);
    private final WebDriverWait wait;
    private final StateHolder holder = StateHolder.getInstance();
    private final HeaderWrap headerWrap;
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final String PRICE_SALE_CLASS = "product__price--sale";
    private final String PRICE_LIST_CLASS = "product__price--list";

    @FindBy(id = "c-product__price-colors")
    private WebElement price_colors;
    @FindBy(id = "c-product__sizes")
    private WebElement sizes;
    @FindBy(id = "c-product__price")
    private WebElement price;
    @FindBy(id = "c-product__variations")
    private WebElement variations;
    @FindBy(id = "c-product__quantity")
    private WebElement product_quantity;
    @FindBy(id = "btn__add-to-bag")
    private WebElement addToBagButton;
    @FindBy(id = "c-product__overview")
    private WebElement productOverview;
    @FindBy(id = "c-product__reviews--ratings-summary")
    private WebElement reviewSummary;
    @FindBy(id = "c-product__sold-out")
    private WebElement soldoutMessage;
    @FindBy(id = "page__p")
    private WebElement page__p;

    public ProductDetails(WebDriver driver) {
        this.driver = driver;
        wait = Util.createWebDriverWait(driver);
        headerWrap = new HeaderWrap(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(reviewSummary));
    }

    public void selectRandomColor() {
        wait.until(ExpectedConditions.visibilityOf(price_colors));
        List<WebElement> availableColors =
                price_colors.findElements(By.xpath(".//li[@class='js-product__color colors-list__item']"));

        if (availableColors.size() > 0) {
            WebElement selectedColor = Util.randomIndex(availableColors);

            selectedColor.click();
        }
    }

    public void selectRandomSize() {
        wait.until(ExpectedConditions.visibilityOf(sizes));
        String availableSizesSelector = ".//li[contains(@class,'js-product__size sizes-list__item') " +
                "and not(contains(@class,'is-unavailable')) " +
                "and not(contains(@class,'is-selected'))]";
        List<WebElement> availableSizes = sizes.findElements(By.xpath(availableSizesSelector));

        if (availableSizes.size() > 0) {
            final WebElement selectedSize = Util.randomIndex(availableSizes);

            selectedSize.click();
        }
    }

    public void selectRandomQty() {
        wait.until(ExpectedConditions.visibilityOf(product_quantity));
        Select qty = new Select(product_quantity.findElement(By.tagName("select")));

        int availableQty = qty.getOptions().size();
        if (availableQty > 1) {
            int randomQty = Util.randomIndex(availableQty - 1) + 1; //

            qty.selectByValue(Integer.toString(randomQty));
        }
    }

    private String getSelectedColor() {
        WebElement selectedColor = price_colors.findElement(By.xpath(".//li[contains(@class,'is-selected')]"));
        return selectedColor.getAttribute("data-name");
    }

    private String getSelectedSize() {
        WebElement selectedSize = sizes.findElement(
                By.xpath(".//li[contains(@class,'js-product__size') and contains(@class,'is-selected')]"));
        return selectedSize.getAttribute("data-name");
    }

    private String getProductName() {
        WebElement name = productOverview.findElement(By.tagName("h1"));
        return name.getText();
    }

    private String getQuantity() {
        Select qty = new Select(product_quantity.findElement(By.tagName("select")));
        return qty.getFirstSelectedOption().getText();
    }

    private String getPrice() {
        List<WebElement> priceGroups = price_colors.findElements(By.xpath(".//div[@class='product__group']"));
        WebElement productPrice;

        if (priceGroups.size() > 1) {
            WebElement selectedColor = price_colors.findElement(By.xpath(".//li[contains(@class,'is-selected')]"));
            productPrice = selectedColor.findElement(By.xpath(".//ancestor::div[@class='product__group']/span"));
        } else {
            //if has variations, get price from variations
            List<WebElement> variationsPrice = variations.findElements(By.tagName("li"));
            if (variationsPrice.size() > 0) {
                WebElement selectedVariation = variations.findElement(By.className("is-selected"));

                //check if variation has sale price
                productPrice = selectedVariation.findElement(
                        By.xpath(".//span[contains(@class,'" + PRICE_SALE_CLASS + "')]"));
                if (!productPrice.isDisplayed()) {
                    //if no sale price get regular price
                    productPrice = selectedVariation.findElement(
                            By.xpath(".//span[contains(@class,'" + PRICE_LIST_CLASS + "')]"));
                }

            } else { //if no variations, get sale price
                productPrice = price.findElement(By.className(PRICE_SALE_CLASS));
                if (!productPrice.isDisplayed()) {
                    //if no sale price get regular price
                    productPrice = price.findElement(By.className(PRICE_LIST_CLASS));
                }
            }
        }

        return productPrice.getText();
    }

    private boolean isSoldOut() {
        List<WebElement> message = soldoutMessage.findElements(By.className("product__sold-out"));

        return message.size() > 0;
    }

    public Product getProduct() {
        Product product = new Product();
        product.setName(getProductName());

        if (!isSoldOut()) {
            product.setColor(getSelectedColor());
            product.setSize(getSelectedSize());
            product.setQuantity(getQuantity());
            product.setPrice(getPrice());
        } else {
            product.setSoldOut(true);
        }

        return product;
    }

    public void addToBag() {
        Stack<Product> productsInBag = (Stack<Product>) holder.get("bag_items");

        if (productsInBag == null) {
            productsInBag = new Stack<>();
        }

        Product product = getProduct();

        productsInBag.add(product);

        logger.info("Adding to bag {}", product);
        holder.put("bag_items", productsInBag);

        addToBagButton.click();
        headerWrap.waitUntilNoCheckOutDropdown();
    }

    private boolean verifyCurrency(String currency) {
        List<WebElement> salePrices = page__p.findElements(By.className(PRICE_SALE_CLASS));
        List<WebElement> listPrices = page__p.findElements(By.className(PRICE_LIST_CLASS));

        List<WebElement> allPrices = new ArrayList<>();
        allPrices.addAll(salePrices);
        allPrices.addAll(listPrices);

        Iterator<WebElement> allPricesIterator = allPrices.iterator();

        boolean result = true;

        while (result & allPricesIterator.hasNext()) {
            WebElement priceElement = allPricesIterator.next();

            if(priceElement.isDisplayed()) {
                String price = priceElement.getText();

                result = CurrencyChecker.anyPriceType(currency, price);

                if (!result) {
                    logger.error("PDP: Not able to check price currency format {}", price);
                }
            }

        }


        return result;
    }

    public boolean verifyContext() {
        Country country = (Country) stateHolder.get("context");
        String currency = country.getCurrency();

        boolean result = verifyCurrency(currency);
        result &= Util.countryContextURLCompliance(driver, country);

        return result;
    }
}
