package com.jcrew.page;

import com.jcrew.page.header.HeaderLogo;
import com.jcrew.page.header.HeaderWrap;
import com.jcrew.pojo.Product;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.JavascriptExecutor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 7/20/16.
 */
public abstract class Array extends PageObject{

    //protected final String PRODUCT_TITLE_CLASS = "c-product-tile";
	protected final String PRODUCT_TITLE_CLASS = "product-tile--content";
    protected final String PRICE_LIST_CLASS = "tile__detail--price--list";
    protected final String PRICE_SALE_CLASS = "tile__detail--price--sale";
    protected final String PRICE_WAS_CLASS = "tile__detail--price--was";
    protected final String NAME_CLASS = "tile__detail--name";

    protected final String VARIATION_CLASS = "tile__detail--alsoin";
    protected final String SHIPPING_CLASS = "tile__detail--shipping";
    protected final String COLOR_COUNTS_CLASS = "js-tile__detail--colors-count";
    protected final String CUSTOM_MESSAGE = "monetate_custom_index_text";

    protected Footer footer;
    protected HeaderWrap header;

    private final Actions hoverAction;

    
    @FindBy(xpath = "//div[@class='product__grid']")
    private WebElement productGrid;


    public Array(WebDriver driver) {
        super(driver);
        this.hoverAction = new Actions(driver);
    }

    protected List<WebElement> getProductTiles(WebElement productList) {
    	//Util.waitWithStaleRetry(driver, productList);
        //List<WebElement> products= productList.findElements(By.className(PRODUCT_TITLE_CLASS));
    	List<WebElement> products= driver.findElements(By.className(PRODUCT_TITLE_CLASS));
        
        logger.debug("This array has {} products",  products.size());
        return products;
    }

    protected List<WebElement> getVariationProductsList(WebElement productList){
        return productList.findElements(By.xpath(".//div[@class='"+PRODUCT_TITLE_CLASS+"' and contains(.,'also in')]"));

    }

    protected List<String> getProductPrices(WebElement productList,String priceType) {
        List<WebElement> priceList = productList.findElements(By.className(priceType));
        List<String> productPrices = new ArrayList<>(priceList.size());

        for (WebElement price:priceList ) {
        	String priceText = price.getText().trim();
        	priceText = priceText.replaceAll("your price ", "");
        	priceText = priceText.replaceAll("valued at ", "");
            productPrices.add(priceText);
        }

        return productPrices;
    }

    protected void selectRandomQS(WebElement productList,boolean isVariationProducts) {
        List<WebElement> productTiles;
        if(isVariationProducts){
            productTiles = getVariationProductsList(productList);
        }else{
            productTiles = getProductTiles(productList);
        }
        logger.info("This array page has {} products", productTiles.size());
        WebElement random_product_tile = Util.randomIndex(productTiles);
        clickQuickShop(random_product_tile);
        new QuickShop(driver);
    }

    protected void selectRandomProduct(WebElement productList,boolean isVariationProducts) {
        List<WebElement> productTiles;
        if (isVariationProducts) {
            productTiles = getVariationProductsList(productList);
        } else {
            productTiles = getProductTiles(productList);
        }

        logger.info("This array page has {} products", productTiles.size());
        WebElement random_product_tile = Util.randomIndex(productTiles);
        clickProduct(random_product_tile);

        HeaderLogo logo = new HeaderLogo(driver);
        logo.hoverLogo();
    }

    public void clickProduct(WebElement product) {

        wait.until(ExpectedConditions.visibilityOf(product));

        WebElement product_name = product.findElement(By.className(NAME_CLASS));
        logger.info("Click on product: {}", product_name.getText());

        WebElement image = product.findElement(By.tagName("img"));
        WebElement link = product.findElement(By.className("product-tile__link"));

        stateHolder.put("fromArray", getProduct(product));
        stateHolder.put("urlFromArray", link.getAttribute("href"));

        wait.until(ExpectedConditions.visibilityOf(image));
        wait.until(ExpectedConditions.elementToBeClickable(image));
        String url = driver.getCurrentUrl();
        //image.click();
        Util.scrollAndClick(driver, image);
        Util.waitLoadingBar(driver);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
    }
    
    private void clickQuickShop(WebElement product) {

        wait.until(ExpectedConditions.visibilityOf(product));
        WebElement product_name = product.findElement(By.className(NAME_CLASS));
        logger.info("Click on quick shop of product : {}", product_name.getText());
        hoverAction.moveToElement(product).perform();
        WebElement qs = product.findElement(By.xpath(".//button[contains(@class,'js-product-tile-quickshop')]/div"));
        hoverAction.moveToElement(qs);
        hoverAction.perform();
        stateHolder.put("fromArray", getProduct(product));
        try{
            qs.click();
        }catch (WebDriverException e){
            logger.error("webdriver exception while trying to bring up quickshop retrying with javascriptExecutor: {}", e.toString());
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", qs);
        }
        
        Util.waitForPageFullyLoaded(driver);
    }
    
    public Product getProduct(WebElement tile) {
        Product product = new Product();
        product.setName(getProductName(tile));
        product.setPrice(getProductPrice(tile));
        logger.info("Picked product: {}", product.getName());
        logger.info("Price: {}", product.getPrice());


        return product;
    }

    private String getProductName(WebElement selectedItem) {
        WebElement name = selectedItem.findElement(By.className("tile__detail--name"));
        return name.getText();
    }

    public int getProductAvailableColors(WebElement selectedItem) {
        int colors = 1;

        List<WebElement> availableColorsTag = selectedItem.findElements(By.className("js-colors__wrap"));

        if(availableColorsTag.size() > 0) {
            WebElement colors__wrap = availableColorsTag.get(0);
            String data_colors = colors__wrap.getAttribute("data-colors-count");
            colors = Integer.parseInt(data_colors);
        }

        return colors;
    }

    public String getProductPrice(WebElement selectedItem) {
        String price;

        List<WebElement> salePrice = selectedItem.findElements(By.className("tile__detail--price--sale"));
        if (salePrice.size() == 1) {
            price = salePrice.get(0).getText();
            price = price.replace("select colors", "").replace("now", "");
        } else {

            WebElement listPrice = selectedItem.findElement(By.className("tile__detail--price--list"));
            price = listPrice.getText();
        }
        return price;
    }
    
    
    public String getProductPriceForSort(WebElement tile) {
        String price;

        List<WebElement> salePrice = tile.findElements(By.className(PRICE_SALE_CLASS));
        if (salePrice.size() == 1) {
            price = salePrice.get(0).getText();
            price = price.replace("now", "");
            if (price.contains("select colors")) {
                price = "";
            }

        } else {
            WebElement listPrice = tile.findElement(By.className("tile__detail--price--list"));

            price = listPrice.getText();
        }

        return price;
    }

}
