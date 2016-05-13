package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.utils.PropertyReader;
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
import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class SearchArray {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(SearchArray.class);
    private final WebDriverWait wait;
    private final StateHolder stateHolder = StateHolder.getInstance();

    private final String PRICE_LIST_CLASS = "tile__detail--price--list";
    private final String NAME_CLASS = "tile__detail--name";
    private final String SHIPPING_CLASS = "tile__detail--shipping";
    private final String PRICE_SALE_CLASS = "tile__detail--price--sale";
    private final String COLOR_COUNTS_CLASS = "js-tile__detail--colors-count";
    private final String CUSTOM_MESSAGE = "monetate_custom_index_text";

    @FindBy(id = "page__search")
    private WebElement pageSearch;
    @FindBy(id = "c-search__results")
    private WebElement searchResults;

    @FindBy(className = "header__search")
    private WebElement headerSearch;

    @FindBy(xpath = "//div[@class='product__grid']")
    private WebElement productGrid;


    public SearchArray(WebDriver driver) {
        this.driver = driver;
        wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(pageSearch));

    }

    public void selectRandomProduct() {
        List<WebElement> productTiles = searchResults.findElements(By.className("c-product-tile"));
        logger.info("This search result page has {} products", productTiles.size());

        WebElement random_product_tile = Util.randomIndex(productTiles);
        wait.until(ExpectedConditions.visibilityOf(random_product_tile));
        WebElement random_product_name = random_product_tile.findElement(By.className(NAME_CLASS));

        logger.info("Selected product: {}", random_product_name.getText());

        WebElement random_product_image = random_product_tile.findElement(By.tagName("img"));
        wait.until(ExpectedConditions.visibilityOf(random_product_image));
        random_product_image.click();

        Util.waitLoadingBar(driver);
    }

    public boolean isSearchPage() {
        Country country = (Country) stateHolder.get("context");
        Util.waitWithStaleRetry(driver, headerSearch);
        wait.until(ExpectedConditions.visibilityOf(searchResults));

        return headerSearch.isDisplayed() && searchResults.isDisplayed() && Util.countryContextURLCompliance(driver, country);
    }


    public void click_first_product_in_grid() {
        Util.waitForPageFullyLoaded(driver);
        final WebElement product = getFirstProduct();
        final WebElement productLink = product.findElement(By.className("product__image--small"));
        wait.until(ExpectedConditions.elementToBeClickable(productLink));
        saveProduct(product);
        productLink.click();
        Util.waitLoadingBar(driver);
    }

    private WebElement getFirstProduct() {
        return getProductTileElements().get(0);
    }

    private List<WebElement> getProductTileElements() {
        wait.until(ExpectedConditions.visibilityOf(productGrid));
        return wait.until(ExpectedConditions.visibilityOfAllElements(productGrid.findElements(By.className("c-product-tile"))));

    }


    private void saveProduct(WebElement productElement) {
        Product product = new Product();
        product.setProductName(getProductName(productElement));
        product.setPriceList(getProductPrice(productElement));

        logger.debug("Selected product is {}", product.getProductName());
        logger.debug("Selected product price is {}", product.getPriceList());

        @SuppressWarnings("unchecked")
        List<Product> productList = (List<Product>) stateHolder.get("productList");

        if (productList == null) {
            productList = new ArrayList<>();
        }

        productList.add(product);
        stateHolder.put("productList", productList);

    }

    private String getProductName(WebElement randomProductSelected) {
        WebElement productName = randomProductSelected.findElement(
                By.xpath(".//a[contains(@class,'product-tile__details')]/span[contains(@class,'tile__detail--name')]"));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productName));
        return productName.getText().trim();
    }

    private String getProductPrice(WebElement productSelected) {
        List<WebElement> productPrices = productSelected.findElements(By.className("tile__detail--price--list"));
        String price = "";
        if (!productPrices.isEmpty()) {
            price = productPrices.get(0).getText().trim();
        }

        return price;
    }


}
