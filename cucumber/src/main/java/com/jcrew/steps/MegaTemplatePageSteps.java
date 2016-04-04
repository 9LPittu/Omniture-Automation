package com.jcrew.steps;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcrew.page.ProductDetailPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import static org.junit.Assert.*;

public class MegaTemplatePageSteps extends DriverFactory {
	
	private final ProductDetailPage productDetailPage = new ProductDetailPage(getDriver());
    
    private final StateHolder stateHolder = StateHolder.getInstance();
	public static Map<String,String> dbResultsMap = new HashMap<String,String>();
	public static Map<String,Boolean> dbFeedResultsMap = new HashMap<String,Boolean>();
	private final Logger logger = LoggerFactory.getLogger(MegaTemplatePageSteps.class);
	
	
	@And("^user navigates to url and should see the PDP page$")
	public void navigate_to_url_and_verify_pdp_page_is_displayed(){
		
		dbResultsMap =  (Map<String, String>) stateHolder.get("dbresults");
		
		boolean productMismatch = false;
		boolean isPDPPageDisplayed = true;
		 
		if ((boolean) stateHolder.get("EmptyResults")){
			
			logger.error("No records are found for the Query ");
		}
		else{
		for(int index=1;index<=(dbResultsMap.size()/3);index++){
		   WebDriver driver = getDriver();

		  String url = dbResultsMap.get("url" + index);
		   driver.get(url);
		   
		   logger.info("Navigated to URL: " + url);
		   
		   String expectedProductName = dbResultsMap.get("megaProduct" + index);
		   expectedProductName=expectedProductName.replaceAll("&reg;","");
		   expectedProductName=expectedProductName.replaceAll("&trade;","");
		   expectedProductName=expectedProductName.replaceAll("&amp;","&");
		   expectedProductName=expectedProductName.replaceAll("&eacute;","");
		   String userEmail = dbResultsMap.get("megaUser" + index);
		   
		   PropertyReader propertyReader = PropertyReader.getPropertyReader();
		   String browserName = propertyReader.getProperty("browser");
		   
		   String actualProductName = "";
		   String itemCode = (url.split("/")[url.split("/").length - 1]).replace(".jsp", "").toUpperCase();		   
		   
		   if(browserName.equalsIgnoreCase("androidchrome") || browserName.equalsIgnoreCase("iossafari")){
			   //for mobile browsers
			   driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
			   try{				   
				   Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//ul[@class='bullet-list']/li[contains(text(),'" + itemCode + "')]"))));
			   }
			   catch(Exception e){
				   logger.error("PDP page is not displayed for the URL " + url);
				   isPDPPageDisplayed = false;
				   continue;
			   }
			   
			   if( !driver.findElement(By.tagName("body")).getAttribute("id").equalsIgnoreCase("multiPDP")){
				   actualProductName = productDetailPage.getProductNameFromPDP();
				   
			   }
			   else{
				   actualProductName = productDetailPage.getFirstProductNameFromMultiPDP();
				   }
			   actualProductName=actualProductName.replaceAll("[^\\x00-\\x7F]", "");
			   }
		   
		   else{
			   //for desktop browsers
			   driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
			   
			   try{				   
				   Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//section[@id='description' or @id='description0']/span[@class='item-num' and contains(text(),'" + itemCode + "')] "))));
			   }
			   catch(Exception e){
				   logger.error("PDP page is not displayed for the URL " + url);
				   isPDPPageDisplayed = false;
				   continue;
			   }
			   
			   actualProductName = driver.findElements(By.xpath("//div[@class='product-detail-sku']/descendant::header/h1")).get(0).getText();
			   actualProductName=actualProductName.replaceAll("[^\\x00-\\x7F]", "");
			  
		   }
		   
		   //validate the correct item page is displayed	
		   //strPagerSource.contains(expectedProductName)
		   if(expectedProductName.equalsIgnoreCase(actualProductName)){
			   logger.info("PDP page is correctly displayed for the item '" + expectedProductName + "'");
		   }
		   else{
			   productMismatch = true;
			   logger.error("Mismatch in PDP page. Actual Item Name: " + actualProductName +
					         ". Expected Item Name: " + expectedProductName + ". User email address: " + userEmail);
		   }
		}
	}
		assertFalse("User should see the correct PDP page for the item", productMismatch);
		
		assertTrue("PDP page should be displayed for all the urls navigated", isPDPPageDisplayed);
	}
	
	
	@And("^user navigates to url and should see the PDP page and not see extended size$")
	public void navigate_to_url_and_verify_pdp_page_is_displayed_extended_not_displayed(){
		
		dbResultsMap =  (Map<String, String>) stateHolder.get("dbresults");
		
		boolean productMismatch = false;
		boolean isPDPPageDisplayed = true;
		if ((boolean) stateHolder.get("EmptyResults")){
			
			logger.info("No records are found for the Query ");
		}
		else{
			for(int index=1;index<=(dbResultsMap.size()/3);index++){
			   WebDriver driver = getDriver();
			   
			   String url = dbResultsMap.get("url" + index); 
			   driver.get(url);
			   logger.info("Navigated to URL: " + url);
			   
			   String expectedProductName = dbResultsMap.get("megaProduct" + index);
			   expectedProductName=expectedProductName.replaceAll("&reg;","");
			   expectedProductName=expectedProductName.replaceAll("&trade;","");
			   expectedProductName=expectedProductName.replaceAll("&amp;","&");
			   expectedProductName=expectedProductName.replaceAll("&eacute;","");
			   
			   String userEmail = dbResultsMap.get("megaUser" + index);
			   
			   PropertyReader propertyReader = PropertyReader.getPropertyReader();
			   String browserName = propertyReader.getProperty("browser");
			   
			   String actualProductName = "";
			   String itemCode = (url.split("/")[url.split("/").length - 1]).replace(".jsp", "").toUpperCase();		   
			   
			   if(browserName.equalsIgnoreCase("androidchrome") || browserName.equalsIgnoreCase("iossafari")){
				   //for mobile browsers
				   driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
				   try{				   
					   Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//ul[@class='bullet-list']/li[contains(text(),'" + itemCode + "')]"))));
				   }
				   catch(Exception e){
					   logger.error("PDP page is not displayed for the URL " + url);
					   isPDPPageDisplayed = false;
					   continue;
				   }
				   
				   actualProductName = productDetailPage.getProductNameFromPDP();
				   actualProductName=actualProductName.replaceAll("[^\\x00-\\x7F]", "");
	               actualProductName = actualProductName.replaceAll("(?i)pre-order ","");
			   }
			   else{
				   //for desktop browsers
				   driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
				   
				   try{				   
					   Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@class='item-num' and contains(text(),'" + itemCode + "')]"))));
				   }
				   catch(Exception e){
					   logger.error("PDP page is not displayed for the URL " + url);
					   isPDPPageDisplayed = false;
					   continue;
				   }
				   
				   actualProductName = driver.findElement(By.xpath("//div[@class='product-detail-sku']/descendant::header/h1")).getText();
				   actualProductName=actualProductName.replaceAll("[^\\x00-\\x7F]", "");
	               actualProductName = actualProductName.replaceAll("(?i)pre-order ","");
			   }
			   
			   //validate the correct item page is displayed		   
			   if(expectedProductName.equalsIgnoreCase(actualProductName)){
				   logger.info("PDP page is correctly displayed for the item '" + expectedProductName + "'");
			   }
			   else{
				   productMismatch = true;
				   logger.error("Mismatch in PDP page. Actual Item Name: " + actualProductName +
						         ". Expected Item Name: " + expectedProductName + ". User email address: " + userEmail);
			   }
			   
			   productDetailPage.validate_extended_size_on_pdp_page_is_displayed();
			}
		assertFalse("User should see the correct PDP page for the item", productMismatch);
		assertTrue("PDP page should be displayed for all the urls navigated", isPDPPageDisplayed);
		}
		
	}
	
	
	@And("^user verify feed validation and log the results for the query ([^\"]*)$")
	public void verify_FeedValidation_andLog_the_results(String strquery)throws FileNotFoundException, IOException, ClassNotFoundException{
		
		
		String database = System.getProperty("database", "jcdpdatabase");
    	String databaseFile = database + ".properties";
    	
    	Properties dbProperties = new Properties();
    	
    	dbProperties.load(new FileReader(databaseFile));
    	String strdbQuery = dbProperties.getProperty("db." + strquery);
    	logger.info("stateholder value:" + stateHolder.get("EmptyResult"));
		if((boolean) stateHolder.get("EmptyResult")){
			logger.info("Validate the feed for the Query. '" + strdbQuery + "'");
			assertTrue("Validate the feed for the Query . '" + strdbQuery + "'",true );
		}
		else{
			
			logger.error("Validate the feed for the Query'" + strdbQuery + "'");
			assertTrue("Validate the feed for the Query . '" + strdbQuery + "'",false );
		}
			
	}
	
	
	@And("^user navigates to url and should see the Image$")
	public void navigate_to_url_and_verify_Image_is_displayed__Correctly(){
		
		dbResultsMap =  (Map<String, String>) stateHolder.get("dbresults");
		
		boolean imageMismatch = false;
		boolean isPDPPageDisplayed = true;
		String ActualitemCode="";
		if ((boolean) stateHolder.get("EmptyResults")){
			
			logger.error("No records are found for the Query ");
		}
		else{
		for(int index=1;index<=(dbResultsMap.size()/3);index++){
		   WebDriver driver = getDriver();
		   
		   String url = dbResultsMap.get("url" + index); 
		   driver.get(url);
		   logger.info("Navigated to URL: " + url);
		   
		   String expectedProductName = dbResultsMap.get("megaProduct" + index);
		   expectedProductName=expectedProductName.replaceAll("&reg;","");
		   expectedProductName=expectedProductName.replaceAll("&trade;","");
		   expectedProductName=expectedProductName.replaceAll("&amp;","&");
		   expectedProductName=expectedProductName.replaceAll("&eacute;","");
		   
		   String userEmail = dbResultsMap.get("megaUser" + index);
		   
		   PropertyReader propertyReader = PropertyReader.getPropertyReader();
		   String browserName = propertyReader.getProperty("browser");
		   
		   String actualProductName = "";
		   String imageSource="";
		  // String itemCode = (url.split("/")[url.split("/").length - 1]).replace(".jsp", "").toUpperCase();		   
		   
		   if(browserName.equalsIgnoreCase("androidchrome") || browserName.equalsIgnoreCase("iossafari")){
			   //for mobile browsers
			   driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
			 
			   imageSource = driver.findElement(By.tagName("img")).getAttribute("src");
			   
			  
		   }
		   else{
			   //for desktop browsers
			   driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
			   imageSource = driver.findElement(By.tagName("img")).getAttribute("src");
			  
			   //imageSource = productDetailPage.getProductImageSourceFromPDP();
			   //ActualitemCode = (imageSource.split("/")[imageSource.split("/").length - 1]).split("_")[0];
			
		   }
		   
		   //validate the correct item page is displayed		   
		   if(url.equalsIgnoreCase(imageSource)){
			   logger.info("Navigate to correct Image for the item '" + expectedProductName + "'");
		   }
		   else{
			   imageMismatch = true;
			   logger.error("Image Mismatch for the Item. Actual Image: " + imageSource +
					         ". Expected Image: " + url + ". User email address: " + userEmail);
		   }
		}
	}
		assertFalse("User should navigate to correct Image on for the item", imageMismatch);
		
		//assertTrue("PDP page should be displayed for all the urls navigated", isPDPPageDisplayed);
	}

	@And("^user navigates to url and should see the Shopping Bag page$")
	public void navigate_to_url_and_verify_SB_page_is_displayed(){

		dbResultsMap =  (Map<String, String>) stateHolder.get("dbresults");

		boolean isSBPageDisplayed = true;

		if ((boolean) stateHolder.get("EmptyResults")){

			logger.error("No records are found for the Query ");
		}
		else{
			for(int index=1;index<=(dbResultsMap.size()/3);index++){
				WebDriver driver = getDriver();

				String url = dbResultsMap.get("url" + index);

				driver.get(url);

				logger.info("Navigated to URL: " + url);

				String expectedProductName = dbResultsMap.get("megaProduct" + index);
				expectedProductName=expectedProductName.replaceAll("&reg;","");
				expectedProductName=expectedProductName.replaceAll("&trade;","");
				expectedProductName=expectedProductName.replaceAll("&amp;","&");
				expectedProductName=expectedProductName.replaceAll("&eacute;","");
				String userEmail = dbResultsMap.get("megaUser" + index);

				PropertyReader propertyReader = PropertyReader.getPropertyReader();
				String browserName = propertyReader.getProperty("browser");

				if(browserName.equalsIgnoreCase("androidchrome") || browserName.equalsIgnoreCase("iossafari")){
					//for mobile browsers
					driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
					try{
						if(stateHolder.get("oldurl")!=null){
							String oldUrl = (String) stateHolder.get("oldurl");
							Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlMatches(oldUrl)));
						}

						Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='order-listing']"))));
					}
					catch(Exception e){
						logger.error("Shopping bag page is not displayed for the URL " + url);
						isSBPageDisplayed = false;
						continue;
					}
				}

				else{
					//for desktop browsers
					driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);

					try{
						Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='order-listing']" ))));
						logger.info("Shopping bag page is displayed");
					}
					catch(Exception e){
						logger.error("Shopping Bag page is not displayed for the URL " + url);
						isSBPageDisplayed = false;
						continue;
					}
				}

				stateHolder.put("oldurl",url);
			}
		}
		assertTrue("Shopping bag page should be displayed for all the urls navigated", isSBPageDisplayed);
	}

}
