package com.jcrew.page;

import com.jcrew.pojo.Product;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiplePdpPage {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(MultiplePdpPage.class);
    private final StateHolder stateHolder = StateHolder.getInstance();

    @FindBy (id = "c-tray__pagination")
    private WebElement paginationSection;
    @FindBy (id = "c-tray__list")
    private WebElement multipleProductSection;

    private List<WebElement> products = null;
    private List<WebElement> productsImages = null;
    private int numProducts;
    private WebElement next;
    private WebElement previous;
    private WebElement trayCount;
    private WebElement article;
    private WebElement productVariations;
    private WebElement productColorSwatches;

    private WebDriverWait wait;

    public MultiplePdpPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;

        wait = Util.createWebDriverWait(driver);
        Util.waitLoadingBar(driver);
        loadNavigation();
    }

    private void loadNavigation(){
        Util.waitWithStaleRetry(driver, paginationSection);
        previous = paginationSection.findElement(By.className("pagination__item--previous"));
        next = paginationSection.findElement(By.className("pagination__item--next"));

        Util.waitWithStaleRetry(driver, multipleProductSection);
        products = multipleProductSection.findElements(By.className("js-tray__item"));
        productsImages = multipleProductSection.findElements(By.className("tray__image--thumbnail"));
        trayCount =  multipleProductSection.findElement(By.className("tray--count"));

        numProducts = products.size();

        //product details
        article = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("c-product")));
        productVariations = article.findElement(By.id("c-product__variations"));
    }

    public boolean containsNavigation(){
        return previous.isDisplayed() && next.isDisplayed();
    }

    public boolean isArrowDisabled(String arrow) {
        String state;
        WebElement link;
        By linkXpath = By.xpath(".//*[contains(@class,'pagination__link')]");

        if ("next".equalsIgnoreCase(arrow)) {
            link = next.findElement(linkXpath);
            logger.debug("next link: {} and class {}", link.getTagName(), link.getAttribute("class"));
        } else if ("previous".equalsIgnoreCase(arrow)) {
            link = previous.findElement(linkXpath);
            logger.debug("previous link: {} and class {}", link.getTagName(), link.getAttribute("class"));
        } else {
            logger.debug("bad use of isArrowDisabled(String)");
            return false;
        }

        state = link.getAttribute("class");

        return state.contains("is-disabled");
    }

    public int getSelectedProductIndex(){
        for (int i = 0; i < products.size(); i++) {
            WebElement product = products.get(i);
            String productClass = product.getAttribute("class");
            if(productClass.contains("is-selected")){
                return i;
            }
        }

        return -1;
    }

    public void setSelectProductIndex(int number){
        //I am not sure if this will work in case the multiple pdp page
        // has more products that are not visible in the screen
        WebElement selected;
        String productCode;

        if(number < 0){
            number = numProducts - 1;
        } else if (number >= numProducts){
            logger.debug("Bad use of setSelectProductIndex");
            return;
        }

        selected = productsImages.get(number);
        productCode = products.get(number).getAttribute("data-code");

        Util.waitForPageFullyLoaded(driver);
        stateHolder.put("shoppableTrayProduct", article);
        selected.click();
        wait.until(ExpectedConditions.urlContains("itemCode="+productCode));
        loadNavigation();
    }

    public void clickNext(){
        stateHolder.put("shoppableTrayProduct", article);
        WebElement nextLink = next.findElement(By.tagName("a"));
        nextLink.click();
        loadNavigation();
    }

    public void clickPrevious() {
        stateHolder.put("shoppableTrayProduct", article);
        WebElement previousLink = previous.findElement(By.tagName("a"));
        previousLink.click();
        loadNavigation();
    }

    public boolean hasProductChanged(){
        WebElement lastSelectedProduct = (WebElement) stateHolder.get("shoppableTrayProduct");
        return !lastSelectedProduct.equals(article);
    }

    public boolean checkEveryItemDetails(){
        //assuming we are in product 0
        boolean result = true;

        for (int i = 1; i < numProducts && result; i++) {
            result = checkDetails();
            WebElement nextProduct = products.get(i);
            String productCode = nextProduct.getAttribute("data-code");
            clickNext();
            wait.until(ExpectedConditions.urlContains("itemCode="+productCode));
        }

        return result;
    }

    private boolean checkDetails() {
        boolean detailsCheck = true;
        WebElement detail;

        //contains product name
        detail = article.findElement(By.className("product__name"));
        detailsCheck &= !detail.getText().isEmpty();
        logger.debug("Name: {}", detailsCheck);

        //contains image
        detail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("product__image")));
        detailsCheck &= detail.isDisplayed();
        logger.debug("Image: {}", detailsCheck);

        //contains price
        try{
            List<WebElement> variationsType = productVariations.findElements(By.className("variations-list"));

            logger.debug("We have variations, checking... ");
            for(WebElement variation:variationsType){
                variation.click();
                detail = productVariations.findElement(By.className("product__price--list"));
                detailsCheck &= detail.isDisplayed();
            }
        }catch (NoSuchElementException noVariations) {
            //product does not have variations
            logger.debug("No variations, checking regular price");
            detail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product__price--list")));
            detailsCheck &= detail.isDisplayed();
        }
        logger.debug("Price: {}", detailsCheck);

        //contains color swatches
        detail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//ul[@class='product__colors colors-list']")));
        detailsCheck &= detail.isDisplayed();
        logger.debug("Colors: {}", detailsCheck);

        //contains sizes options
        detail = article.findElement(By.className("sizes-list"));
        detailsCheck &= detail.isDisplayed();
        logger.debug("Sizes: {}", detailsCheck);

        //contains sizes & fit details
        List<WebElement> sizes = detail.findElements(By.tagName("li"));
        if(sizes.size() > 1) {

            detail = article.findElement(By.className("js-link__size-fit"));
            detailsCheck &= detail.isDisplayed();
            logger.debug("Size & fit: {}", detailsCheck);
        } else {
          logger.debug("One size product, no size and fit details");
        }


        return detailsCheck;
    }

    public boolean itemsNumberMatchesPicturesSize() {
        String itemsString = trayCount.getText();
        itemsString = itemsString.replace(" items", "");
        int itemsNumber = Integer.parseInt(itemsString);

        return itemsNumber == numProducts;
    }
}
