package com.jcrew.steps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcrew.page.ProductDetailPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import com.jcrew.util.DatabasePropertyReader;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import static org.junit.Assert.*;

public class MegaTemplatePageSteps extends DriverFactory {
	
	private final ProductDetailPage productDetailPage = new ProductDetailPage(getDriver());
    
    private final StateHolder stateHolder = StateHolder.getInstance();
	public static Map<String,String> dbResultsMap = new HashMap<String,String>();
	public static Map<String,Boolean> dbFeedResultsMap = new HashMap<String,Boolean>();
	private final Logger logger = LoggerFactory.getLogger(MegaTemplatePageSteps.class);
	private final DatabasePropertyReader dbReader = DatabasePropertyReader.getPropertyReader();
	
	
	@And("^user navigates to each url and see the PDP page$")
	public void navigate_to_each_url_and_verify_PDP_page(){
		boolean extendedSizeVerification = false;
		navigate_to_url_and_verify_pdp_page(extendedSizeVerification);
	}
	
	
	@And("^user navigates to url and should see the PDP page and not see extended size$")
	public void navigate_to_each_url_and_verify_PDP_page_without_extended_size(){
		boolean extendedSizeVerification = true;
		navigate_to_url_and_verify_pdp_page(extendedSizeVerification);
	}
	
	
	@And("^user verify feed validation and log the results for the query ([^\"]*)$")
	public void verify_FeedValidation_andLog_the_results(String strquery)throws FileNotFoundException, IOException, ClassNotFoundException{

    	String strDBConnection = dbReader.getProperty("dbEnvironment");
		String strdbQuery = dbReader.getProperty(strDBConnection+"." + strquery);
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

		if ((boolean) stateHolder.get("EmptyResults")){
			logger.error("No records are found for the Query ");
		}
		else{
		for(int index=1;index<=(dbResultsMap.size()/3);index++){
		   	WebDriver driver = getDriver();
		   
		   	String url = dbResultsMap.get("url" + index);
		   	driver.get(url);
		   	logger.info("Navigated to URL: " + url);

		   	String imageSource="";
			try {
				imageSource = driver.findElement(By.tagName("img")).getAttribute("src");
			}
			catch(Exception e){
				logger.error("Unable to retrieve image source for the URL " + url);
				imageMismatch = true;
				continue;
			}

		   	if(url.equalsIgnoreCase(imageSource)){
			   	logger.info("Navigate to correct Image for the item '" + url + "'");
		   	}
		   	else{
			   	imageMismatch = true;
			   	logger.error("Image Mismatch for the Item. Actual Image: " + imageSource + ". Expected Image: " + url + ".");
		   }
		}
	}
		assertFalse("User should navigate to correct Image on for the item", imageMismatch);
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

				driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
				try{
					if(stateHolder.get("oldurl")!=null){
						String oldUrl = (String) stateHolder.get("oldurl");
						Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlMatches(oldUrl)));
					}

					Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='order-listing']")));
					logger.info("Shopping bag page is displayed for the URL " + url);
				}
				catch(Exception e){
					logger.error("Shopping bag page is not displayed for the URL " + url);
					isSBPageDisplayed = false;
					continue;
				}

				stateHolder.put("oldurl",url);
			}
		}
		assertTrue("Shopping bag page should be displayed for all the urls navigated", isSBPageDisplayed);
	}


	public void navigate_to_url_and_verify_pdp_page(boolean extendedSizeVerification){
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
						Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='bullet-list']/li[contains(text(),'" + itemCode + "')]")));
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
						Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='item-num' and contains(text(),'" + itemCode + "')]")));
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
				if (extendedSizeVerification) {
					productDetailPage.validate_extended_size_on_pdp_page_is_displayed();
				}
			}
			assertFalse("User should see the correct PDP page for the item", productMismatch);
			assertTrue("PDP page should be displayed for all the urls navigated", isPDPPageDisplayed);
		}

	}

}
