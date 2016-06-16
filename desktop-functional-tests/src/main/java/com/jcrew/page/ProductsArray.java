package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ProductsArray {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(ProductsArray.class);
    private final WebDriverWait wait;
    private final Footer footer;
    private final HeaderWrap header;
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final String PRICE_LIST_CLASS = "tile__detail--price--list";
    private final String PRICE_WAS_CLASS = "tile__detail--price--was";
    private final String NAME_CLASS = "tile__detail--name";
    private final String SHIPPING_CLASS = "tile__detail--shipping";
    private final String PRICE_SALE_CLASS = "tile__detail--price--sale";
    private final String COLOR_COUNTS_CLASS = "js-tile__detail--colors-count";
    private final String CUSTOM_MESSAGE = "monetate_custom_index_text";

    @FindBy(id = "c-product__list")
    private WebElement productList;
    @FindBy(id = "c-category__filters")
    private WebElement categoryFilters;
    @FindBy(id = "c-category__item-count")
    private WebElement itemCount;

    public ProductsArray(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);
        this.footer = new Footer(driver);
        this.header = new HeaderWrap(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(productList));
    }

    private List<WebElement> getProductTiles() {
        List<WebElement> productTiles = productList.findElements(By.className("c-product-tile"));

        return productTiles;
    }

    public void selectRandomProduct() {
        List<WebElement> productTiles = getProductTiles();
        logger.info("This subcategory has {} products", productTiles.size());

        WebElement random_product_tile = Util.randomIndex(productTiles);
        wait.until(ExpectedConditions.visibilityOf(random_product_tile));
        WebElement random_product_name = random_product_tile.findElement(By.className(NAME_CLASS));

        logger.info("Selected product: {}", random_product_name.getText());

        WebElement random_product_image = random_product_tile.findElement(By.tagName("img"));
        wait.until(ExpectedConditions.visibilityOf(random_product_image));
        random_product_image.click();

        Util.waitLoadingBar(driver);
        header.reload();
    }

    private boolean verifyCurrency(String currency) {
        boolean result = true;

        List<WebElement> productTiles = getProductTiles();
        Iterator<WebElement> productTilesIterator = productTiles.iterator();
        WebElement tile = null;

        while (result & productTilesIterator.hasNext()) {
            tile = productTilesIterator.next();
            List<WebElement> priceList = tile.findElements(By.className(PRICE_LIST_CLASS));
            List<WebElement> priceWas = tile.findElements(By.className(PRICE_WAS_CLASS));
            List<WebElement> priceSale = tile.findElements(By.className(PRICE_SALE_CLASS));
            WebElement price;
            String priceText;

            if (priceList.size() > 0) {
                price = priceList.get(0);
                priceText = price.getText();

                result = CurrencyChecker.listPrice(currency, priceText);
                if (!result) {
                    logger.error("Array: Not able to check list price currency format for item {}", tile.getText());
                }
            }

            if (priceWas.size() > 0) {
                price = priceWas.get(0);
                priceText = price.getText();

                result &= CurrencyChecker.wasPrice(currency, priceText);
                if (!result) {
                    logger.error("Array: Not able to check was price currency format for item {}", tile.getText());
                }
            }

            if (priceSale.size() > 0) {
                price = priceSale.get(0);
                priceText = price.getText();

                result &= CurrencyChecker.anyPriceSaleType(currency, priceText);
                if (!result) {
                    logger.error("Array: Not able to check sale price currency format for item {}", tile.getText());
                }
            }

        }

        return result;
    }

    public boolean verifyContext() {
        Country country = (Country) stateHolder.get("context");
        String countryFooter = footer.getCountry();

        boolean result = Util.countryContextURLCompliance(driver, country);
        result &= countryFooter.equalsIgnoreCase(country.getName());

        return result;
    }

    public boolean isCorrectCurrencySymbolonProductGridList() {
        Country c = (Country) stateHolder.get("context");

        List<WebElement> productpricess = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//span[contains(@class,'tile__detail--price--')]"))));

        return CurrencyChecker.validatePrices(productpricess, c);

    }

    public String getRefineText() {
        WebElement dropDown = categoryFilters.findElement(By.xpath(".//h3/span[contains(@class,'js-label')]"));
        return dropDown.getText().toLowerCase();
    }

    private void openRefineAccordion() {
        WebElement accordion = categoryFilters.findElement(By.className("js-accordian__wrap"));
        wait.until(ExpectedConditions.visibilityOf(accordion));
        String accordionClass = accordion.getAttribute("class");

        if(!accordionClass.contains("is-expanded")) {
            WebElement accordionHeader = accordion.findElement(By.className("js-accordian__header"));
            accordionHeader.click();
        } else {
            logger.info("Refine dropdown already open");
        }
    }

    public List<String> getRefineOptions() {
        WebElement accordion = categoryFilters.findElement(By.className("js-accordian__wrap"));
        openRefineAccordion();

        List<WebElement> options = accordion.findElements(By.className("accordian__menu__item"));

        List<String> optionsString = new ArrayList<>(options.size());

        for(WebElement option: options) {
            optionsString.add(option.getText().toLowerCase());
        }

        return optionsString;
    }

    public int getNumberOfLists() {
        List<WebElement> lists = productList.findElements(By.xpath(".//div[contains(@class,'product__list')]"));
        return lists.size();
    }

    public List<String> getAvailableLists() {
        List<WebElement> lists = productList.findElements(By.xpath(".//div[contains(@class,'product__list')]/header/h4"));
        List<String> optionsString = new ArrayList<>(lists.size());

        for(WebElement item: lists) {
            optionsString.add(item.getText().toLowerCase());
        }

        return optionsString;
    }

    public String getItemsText() {
        WebElement itemCountElement = itemCount.findElement(By.id("js-products-count"));
        logger.debug("Current category contains {} items", itemCountElement.getText());

        return itemCountElement.getText();
    }

    public void selectRefinement() {
        stateHolder.put("itemsBefore", getItemsText());
        WebElement accordion = categoryFilters.findElement(By.className("js-accordian__wrap"));
        openRefineAccordion();
        List<WebElement> options = accordion.findElements(By.className("accordian__menu__item"));

        int random = Util.randomIndex(options.size() - 1) + 1;
        WebElement selectedOption = options.get(random).findElement(By.tagName("a"));

        logger.debug("Selected {} from refinement", selectedOption.getText());
        stateHolder.put("itemsBefore", getItemsText());
        stateHolder.put("selectedRefinement", selectedOption.getText().toLowerCase());
        selectedOption.click();
    }
}
