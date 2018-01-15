package com.jcrew.page;

import com.jcrew.page.header.HeaderWrap;
import com.jcrew.page.product.ProductDetails;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ArrayCategory extends Array {

	@FindBy(className = "c-product__list")
    private WebElement productList;
	// sorry no items message
	@FindBy(xpath = "//*[@id=\\\"page__c\\\"]/article/div/p[1]")
    private WebElement emptyproductList;
	
    @FindBy(id = "c-category__filters")
    private WebElement categoryFilters;
    @FindBy(id = "c-category__item-count")
    private WebElement itemCount;
    @FindBy(className = "desc_line4")
	private List<WebElement> shopnowlist;
    
    @FindBy(id = "tray__list")
	private WebElement trayList;
    @FindBy(id = "c-category__page-title")
    private WebElement arrayTitle;
    

    public ArrayCategory(WebDriver driver) {
        super(driver);

//        wait.until(ExpectedConditions.visibilityOf(productList));
                
        header = new HeaderWrap(driver);
        Util.scrollPage(driver, "BOTTOM");
        footer = new Footer(driver);
    }

    private List<WebElement> getProductTiles() {
        return getProductTiles(productList);
    }

    public String getArrayTitle() {
        WebElement subCategoty = arrayTitle.findElement(By.xpath(".//div[@class='category__page-title']/*"));
        wait.until(ExpectedConditions.visibilityOf(subCategoty));

        return subCategoty.getText();
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
    	System.out.println("productTiles size.. "+productTiles);
    	
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
        // WebElement dropDown = categoryFilters.findElement(By.xpath(".//*/span[contains(@class,'js-label')]"));
    	WebElement dropDown = categoryFilters.findElement(By.xpath("//*[@id='c-filters__header-item--toggle']"));
    	return dropDown.isDisplayed(); 
        
    }

    public String getRefineText() {
        // WebElement dropDown = categoryFilters.findElement(By.xpath(".//*/span[contains(@class,'js-label')]"));
    	WebElement dropDown = categoryFilters.findElement(By.xpath("//h1[contains(text(),'SWEATERS')]"));    	    	
     	return dropDown.getText().toLowerCase();
    }

    public void openRefineAccordion() {
        WebElement accordion = getAccordianElement();
        String accordionClass = accordion.getAttribute("class");

        if(!accordionClass.contains("is-expanded")) {
            // WebElement accordionHeader = accordion.findElement(By.className("js-accordian__header"));
            WebElement accordionHeader = accordion.findElement(By.xpath("//*[@class='c-filters--label' and contains(text(),'Category')]"));
          
            accordionHeader.click();
            // System.out.println(" Clicked on dropdown.. ");
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
        try {
			Util.waitForPageFullyLoaded(driver);
			wait.until(ExpectedConditions.visibilityOf(productList));
		} catch (Exception e) {
			e.printStackTrace();			
		}
        return productList.isDisplayed();
    }

    public WebElement getAccordianElement() {
        // WebElement accordian = wait.until(ExpectedConditions.visibilityOf(categoryFilters.findElement(By.className("js-accordian__wrap"))));
        WebElement accordian = wait.until(ExpectedConditions.visibilityOf(categoryFilters.findElement(By.xpath("//*[@id='c-filters__header-item--toggle']"))));
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
           // Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));

            //Verify that you have more than one product in tray. If you only have one, then select other tray
            List<WebElement> items = driver.findElements(By.className("js-tray__item"));
            logger.debug("items in tray: {}", items.size());
            if(items.size() == 1){
                driver.navigate().back();
                return false;
            }

            return true;
        
    }
    
}
