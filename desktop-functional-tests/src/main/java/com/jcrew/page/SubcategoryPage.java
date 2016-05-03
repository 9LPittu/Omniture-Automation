package com.jcrew.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcrew.pojo.Product;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;

public class SubcategoryPage {
	
	private final WebDriver driver;
    private final StateHolder stateHolder = StateHolder.getInstance();
    private final Logger logger = LoggerFactory.getLogger(SubcategoryPage.class);
    
    @FindBy(className = "product__grid")
    private WebElement productGrid;
    
    @FindBy(className = "product__name")
    private WebElement productName;
    
    public SubcategoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void selectRandomItemAndSelectSizeColor(){

    	boolean isItemFound = false;
    	int MAX_ITEMS_TO_CHECK = 5;
    	int itemsThreshold;

    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productGrid));

    	List<WebElement> arrayPageItems = driver.findElements(By.xpath("//div[@class='c-product-tile']"));

    	//checking items are displayed on the array page
    	if(arrayPageItems.size()==0){
    		throw new WebDriverException("No items are displayed on the array page (or) element properties are changed");
    	}

    	//Setting the threshold on the items to be checked
    	if(arrayPageItems.size() > MAX_ITEMS_TO_CHECK){
    		itemsThreshold = MAX_ITEMS_TO_CHECK;
    	}
    	else{
    		itemsThreshold = arrayPageItems.size();
    	}
    	
    	String arrayPageItemName = "";
    	String arrayPageItemPrice = "";
    	for(int loopCntr=0;loopCntr<itemsThreshold;loopCntr++){

    		try{
    			arrayPageItems = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@class='c-product-tile']"))));

        		//Capture the item name and price on array page 		
        		List<WebElement> itemName = driver.findElements(By.xpath("//span[contains(@class,'tile__detail--name')]"));
        		WebElement currentItemOnArrayPage = itemName.get(loopCntr);
        		arrayPageItemName = currentItemOnArrayPage.getText().trim();
        		
        		List<WebElement> itemPrice = driver.findElements(By.xpath("//span[contains(@class,'tile__detail--price--list')]"));
        		arrayPageItemPrice = itemPrice.get(loopCntr).getText().trim();

        		//Click on array page item
        		currentItemOnArrayPage.click();
        		logger.debug("Array page item '{}' is clicked", arrayPageItemName);

    			//Wait till the PDP page is displayed
        		Util.waitForPageFullyLoaded(driver);        		
        		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(productName));
        		logger.debug("PDP page is displayed!!!");
        		
        		List<WebElement> itemColors = Util.createWebDriverWait(driver,20).until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath(".//img[@class='colors-list__image']"))));

	  			//If colors are not available then navigate back to array page
	  			if(itemColors.size() == 0){
	  				navigateBackToArrayPage();
	  				continue;
	  			}
	  			else{
	  				logger.debug("Color element(s) found!!!");
	  			}

	  			//Click on each color at random and check any sizes are available
	  			String colorName = "";
	  			boolean isValidItemSizesAvailable = false;
	  			for(WebElement itemColor:itemColors){

	  				itemColor.click();

	  				//check atleast one size is available for a particular color
	  				List<WebElement> itemSizes = driver.findElements(By.xpath("//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]"));
	  				if(itemSizes.size() > 0){
	  					WebElement productPriceColors = driver.findElement(By.xpath("//div[@class='product__price-colors']"));
	  					colorName = productPriceColors.findElement(By.className("product__value")).getText();
	  					isValidItemSizesAvailable = true;
	  					break;
	  				}
	  			}

	  			//If there are no sizes available i.e., item is OUT OF STOCK(OOS) then navigate back to array page
	  			if(!isValidItemSizesAvailable){
	  				navigateBackToArrayPage();
	  				continue;
	  			}

	  			logger.debug("Selected item name: {}", arrayPageItemName);
	  			logger.debug("Selected item price: {}", arrayPageItemPrice);
	  			logger.debug("Selected item color: {}", colorName);

	            //Select random size from the available sizes	  			
	  			List<WebElement> itemSizes = driver.findElements(By.xpath("//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]"));
	  			
	  			int itemSizeIndex = Util.randomIndex(itemSizes.size());	  			
	            String sizeName = itemSizes.get(itemSizeIndex).getText();
	            itemSizes.get(itemSizeIndex).click();
	            logger.debug("Selected item size: {}", sizeName);	            

	            //Save all item related details in stateholder
	            Product product = new Product();

	            product.setProductName(arrayPageItemName);
	            product.setPriceList(arrayPageItemPrice);
	            product.setSelectedColor(colorName);
	            product.setSelectedSize(sizeName);

	            @SuppressWarnings("unchecked")
				List<Product> productList = (List<Product>) stateHolder.get("productList");

	            if (productList == null) {
	                productList = new ArrayList<>();
	            }

	            productList.add(product);
	            stateHolder.put("productList", productList);

	  			isItemFound = true;
	            break;
    		}
    		catch(Exception e){
    			try{
	    			boolean isPDP = driver.findElement(By.xpath(".//h1[@class='product__name']")).isDisplayed();
	    			if (isPDP){
	    				navigateBackToArrayPage();
	    			}
    			}
    			catch(Exception e1){
    				logger.info("PDP page is not displayed");    				
    			}
    		}
    	}

    	if(!isItemFound){
    		throw new WebDriverException("No item is found with appropriate color and size");
    	}
    }

    public void navigateBackToArrayPage(){
    	WebElement breadcrumb = Util.createWebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(driver.findElement(By.className("c-header__breadcrumb"))));
		breadcrumb.findElement(By.xpath("//ul[@class='breadcrumb__list']/li[3]/a[@class='breadcrumb__link']")).click();
		Util.waitForPageFullyLoaded(driver);
		logger.debug("Navigated back to Array page");
    }
}