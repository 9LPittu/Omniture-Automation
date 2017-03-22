package com.jcrew.page;

import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ArrayCategory extends Array{

    @FindBy(id = "c-product__list")
    private WebElement productList;
    @FindBy(id = "c-category__filters")
    private WebElement categoryFilters;
    @FindBy(id = "c-category__item-count")
    private WebElement itemCount;
    @FindBy(className = "desc_line4")
	private List<WebElement> shopnowlist;
    @FindBy(id = "plusArrayContainer")
    private WebElement arrayContainer;
    @FindBy(id = "tray__list")
	private WebElement trayList;
    

    public ArrayCategory(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);
        try {
        	wait.until(ExpectedConditions.visibilityOf(productList));
        } catch (Exception e) {
        	wait.until(ExpectedConditions.visibilityOf(arrayContainer));
        }
        footer = new Footer(driver);
        header = new HeaderWrap(driver);
    }

    private List<WebElement> getProductTiles() {
        return getProductTiles(productList);
    }

    public boolean isCategoryArrayPage(String subCategoryName) {
        WebElement subCategoty = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h2[" + Util.xpathGetTextLower + "='" + subCategoryName.toLowerCase() + "']"))));
        return subCategoty.isDisplayed();
    }


    public List<String> getPrices() {

        return getProductPrices(productList,PRICE_LIST_CLASS);
    }

    public List<String> getWasPrices() {
        return getProductPrices(productList,PRICE_WAS_CLASS);
    }

    public List<String> getSalePrices() {
        return getProductPrices(productList,PRICE_SALE_CLASS);
    }

    public void selectRandomQS(String type) {
        if ("variation".equalsIgnoreCase(type)) {
            selectRandomQS(productList,true);
        } else {
            selectRandomQS(productList,false);
        }
    }
    
    public void selectFirstProduct(){
    	List<WebElement> productTiles = getProductTiles();    	
    	clickProduct(productTiles.get(0));
        new ProductDetails(driver);
    }

    public void selectRandomProduct(String type) {
        if("variation".equalsIgnoreCase(type)) {
            selectRandomProduct(productList,true);
        } else {
            selectRandomProduct(productList,false);
        }
    }

    public boolean isRefineDropdownDisplayed() {
        WebElement dropDown = categoryFilters.findElement(By.xpath(".//h3/span[contains(@class,'js-label')]"));
        return dropDown.isDisplayed();
    }

    public String getRefineText() {
        WebElement dropDown = categoryFilters.findElement(By.xpath(".//h3/span[contains(@class,'js-label')]"));
        return dropDown.getText().toLowerCase();
    }

    public void openRefineAccordion() {
        WebElement accordion = getAccordianElement();
        String accordionClass = accordion.getAttribute("class");

        if(!accordionClass.contains("is-expanded")) {
            WebElement accordionHeader = accordion.findElement(By.className("js-accordian__header"));
            accordionHeader.click();
        } else {
            logger.info("Refine dropdown already open");
        }
    }

    public String retrieveDropdownState(){
        WebElement accordion = getAccordianElement();
        String accordionClass = accordion.getAttribute("class");

        if(accordionClass.contains("is-expanded")) {
            return "open";
        } else {
            return "closed";
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
    public boolean isCategoryArray(){
        Util.waitForPageFullyLoaded(driver);
        wait.until(ExpectedConditions.visibilityOf(productList));
        return productList.isDisplayed();
    }

    public WebElement getAccordianElement() {
        WebElement accordian = wait.until(ExpectedConditions.visibilityOf(categoryFilters.findElement(By.className("js-accordian__wrap"))));
        return accordian;
    }
    
    public boolean selectTheRandomProductForShoppableTray(){
    	logger.debug("Selecting a random shop the look for {}");
        Util.waitLoadingBar(driver);
        
            int randomIndex = Util.randomIndex(shopnowlist.size());
            WebElement randomShopTheLook = shopnowlist.get(randomIndex);
            logger.debug("Picked look #{}", randomIndex);
            Util.waitLoadingBar(driver);
            Util.scrollToElement(driver,randomShopTheLook);
            Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(randomShopTheLook));
            String url = driver.getCurrentUrl();
            Util.clickOnElement(driver, randomShopTheLook);
            Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));

            //Verify that you have more than one product in tray. If you only have one, then select other tray
            List<WebElement> items = trayList.findElements(By.className("js-tray__item"));
            logger.debug("items in tray: {}", items.size());
            if(items.size() == 1){
                driver.navigate().back();
                return false;
            }

            return true;
        
    }
    
}
