package com.jcrew.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ClearCacheAndNavigateAllSubCategories {

	public static void main(String[] args) throws InterruptedException {
		
		PropertyReader propertyReader = PropertyReader.getPropertyReader();
    	String url = propertyReader.getEnvironment();    	
    	url = url.replace("http:", "https:");
    	
    	DriverFactory driverFactory = new DriverFactory();
        WebDriver driver = driverFactory.getDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        
        driver.get(url + "/cc.jsp");
        
        //Click on Clear! button against the PDPDataCache 
        WebElement pdpDataCacheClearButton = driver.findElement(By.xpath("//td[contains(text(), 'PDPDataCache')]/following-sibling::td/button[text()='Clear!']"));
        pdpDataCacheClearButton.click();
        
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h2[contains(text(),'cleared cache: PDPDataCache')]"))));
        
        //Click on Clear! button against the skuIDToInventoryNew      
        WebElement skuIDToInventoryNewClearButton = driver.findElement(By.xpath("//td[contains(text(), 'skuIDToInventoryNew')]/following-sibling::td/button[text()='Clear!']"));
        skuIDToInventoryNewClearButton.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h2[contains(text(),'cleared cache: skuIDToInventoryNew')]"))));        
        
        //Navigate to each category & each subcategory
        driver.navigate().to(url); 
        
        WebElement mainMenu = driver.findElement(By.className("primary-nav__text"));
        mainMenu.click();
        
        List<WebElement> categories = driver.findElements(By.className("menu__link--forward"));
        System.out.println("Categories count# " + categories.size());
        for(int i=0;i<categories.size();i++){
        	String categoryName = categories.get(i).getText().trim();
        	if(!categoryName.equals("")){
        		System.out.println(categoryName);
        		categories.get(i).click();
        		Thread.sleep(1000);
        		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("menu__link--header"))));        		      		
        		List<WebElement> subCategories = driver.findElements(By.className("menu__link--has-href"));
        		System.out.println("Subcategories count# " + subCategories.size());
        		for(int j=0;j<subCategories.size();j++){
        			
        			boolean blnArrayPageDisplayed = false;
        			boolean blnSubCategoryTitleDisplayed = false;
        			boolean blnBrowserTitleContainsSubcategory = false;
        			
        			String subCategoryName = subCategories.get(j).getText().trim();
        			if(!subCategoryName.equals("")){
        				System.out.print("      ");
        				System.out.println(subCategoryName);
        				subCategories.get(j).click();
        				Thread.sleep(1000);
        				
        				//Verify Subcategory heading is displayed
        				try{
        					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h2[text()='" + subCategoryName.toUpperCase() + "']"))));
        					blnSubCategoryTitleDisplayed = true;
        				}
        				catch(Exception e){
        					blnSubCategoryTitleDisplayed = false;
        				}

        				//Verify array page is displayed
        				try{
        					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("product__image--small"))));
        					blnArrayPageDisplayed = true;
        				}
        				catch(Exception e){
        					blnArrayPageDisplayed = false;
        				}
        				
        				//Verify browser title contains subcategory
        				try{
        					wait.until(ExpectedConditions.titleContains(subCategoryName));
        					blnBrowserTitleContainsSubcategory = true;
        				}
        				catch(Exception e){
        					blnBrowserTitleContainsSubcategory = false;
        				}
        				
        				
        				if(waitTillPageIsLoaded(driver) && (blnArrayPageDisplayed || blnSubCategoryTitleDisplayed)){
        					System.out.print("      ");
        					System.out.println(subCategoryName + " page is loaded");       					
        				}
        				else{
        					System.out.print("      ");
        					System.err.println(subCategoryName + " page is NOT loaded. Timeout is reached");
        				}
        			}
        			else{
        				continue;
        			}
        			
        			mainMenu.click();
        			Thread.sleep(500);
        		}
        	}
        	else{
        		continue;
        	}
        	
        	List<WebElement> backButton = driver.findElements(By.xpath("//span[@class='btn__label' and text()='BACK']"));
        	backButton.get(0).click();
        	Thread.sleep(500);
        }

	}
	
	public static boolean waitTillPageIsLoaded(WebDriver driver) throws InterruptedException{
		
		boolean blnFlag = false;
		int intCounter = 0;
		
    	while(true){
    		if(((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete")){
    			blnFlag = true;
    			break;
    		}
    		else{
    			Thread.sleep(1000);
    			intCounter++;
    			if(intCounter>=60){
    				blnFlag = false;    				
    				break;
    			}    			
    		}
    	}
    	
    	return blnFlag;
	}
}