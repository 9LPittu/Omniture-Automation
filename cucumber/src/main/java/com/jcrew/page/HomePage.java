package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jcrew.util.PropertyReader;

import java.util.List;

public class HomePage {

    private final WebDriver driver;
    private Logger logger = LoggerFactory.getLogger(HomePage.class);
    private final StateHolder stateHolder = StateHolder.getInstance();


    @FindBy(id = "lightbox")
    private WebElement modalWindow;

    @FindBy(className = "closePopup")
    private WebElement closePopupLink;

    @FindBy(id = "page__home")
    private WebElement pageHome;

    @FindBy(className = "header__search__input")
    private WebElement searchInput;

    @FindBy(className = "header__search__button--find")
    private WebElement headerSearchButtonFind;

    @FindBy(className = "c-email-capture")
    private WebElement emailCaptureSection;
    
    @FindBys({@FindBy(xpath="//div[@id='global__email-capture']/section/div[contains(@class,'email-capture--close')]")})
    private List<WebElement> emailCaptureClose;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        
//        Commenting the below code as it may be required in future
        
//        if(!driver.getTitle().contains("Factory")){
//        	if(!driver.getCurrentUrl().contains("sidecar=false")){
//        		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
//        		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("footer__country-context")));
//        	}
//        	else{
//        		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.id("globalHeader")));
//        		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.id("ftr")));
//        	}
//        }
//        else{
//        	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("headerPanel")));
//        	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.id("un_footer")));
//        }
        
        if(!driver.getCurrentUrl().contains("sidecar=false")){
    		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
    		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("footer__country-context")));
    	}
    	else{
    		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.id("globalHeader")));
    		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.id("ftr")));
    	}
    }

    public void hit_enter_in_search_field() {
        searchInput.sendKeys(Keys.ENTER);
        Util.waitLoadingBar(driver);
    }

    public void input_search_term(String searchTerm) {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(searchTerm);
        logger.info("Input search term: {}", searchTerm);
    }


    public void click_on_search_button_for_input_field() {
        String url = driver.getCurrentUrl();
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerSearchButtonFind));
        headerSearchButtonFind.click();
        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
        Util.waitLoadingBar(driver);
    }

    public boolean isHomePage() {
        Country country = (Country)stateHolder.get("context");
        boolean isDisplayed = Util.waitWithStaleRetry(driver,pageHome);
        boolean isURL = Util.countryContextURLCompliance(driver,country);

        return isDisplayed & isURL;
    }

    public void close_email_pop_up() {
        emailCaptureSection.findElement(By.className("js-email-capture--close")).click();
    }

    public void handle_email_pop_up() {

        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        boolean emailCapture = jse.executeScript("return jcrew.config.showEmailCapture;").equals(true);
        logger.debug("Email capture? {}", emailCapture);

        if(emailCapture) {                    
            try{
	            List<WebElement> email_capture = Util.createWebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfAllElements(emailCaptureClose));	            
	            if(email_capture.size() > 0) {
	            	logger.debug("Email capture on, let's turn it off!!");
	                WebElement close = email_capture.get(0);
	                Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(close));
	                close.click();
	                
	                refreshBrowserIfEmailCapturePersists();
	            }
            }
            catch(Exception e){
            	refreshBrowserIfEmailCapturePersists();
            	logger.debug("No email capture displayed...");
            }            
        }
    }
    
    public void refreshBrowserIfEmailCapturePersists(){
    	PropertyReader propertyReader = PropertyReader.getPropertyReader();
        if(propertyReader.getProperty("browser").equalsIgnoreCase("chrome")){
        	try{
        		List<WebElement> email_capture = Util.createWebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfAllElements(emailCaptureClose));
            	if(email_capture.size() > 0){
            		driver.navigate().refresh();
	            	Util.waitLoadingBar(driver);
            	}
        	}
        	catch(Exception e1){
        		logger.debug("No email capture displayed...");
        	}
        }
    }

    public boolean isEmailPopUpNotDisplayed() {
        try {
            return !(emailCaptureSection.isDisplayed());
        } catch (NoSuchElementException ne) {
            logger.debug("Email capture pop up is not present");
            return true;
        }
    }

    public boolean isEmailPopUpDisplayed() {
        try {
            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(emailCaptureSection));
            return emailCaptureSection.isDisplayed();
        } catch (NoSuchElementException ne) {
            logger.debug("Email capture pop up is not present");
            return false;
        }
    }

    public void enter_email_address() {
        try {
            emailCaptureSection.findElement(By.className("js-email-capture--input")).sendKeys("test@example.org");
        }catch (NoSuchElementException e) {
            logger.debug("Email capture pop up is not present");
        }
    }

    public void click_on_the_arrow_button_to_submit(){
        try {
            emailCaptureSection.findElement(By.className("js-email-capture--button")).click();
        }catch (NoSuchElementException e) {
            logger.debug("Email capture pop up is not present");
        }

    }

   public void searchItemByReadingPropertyFile(String propertyName){
	   	PropertyReader propertyReader = PropertyReader.getPropertyReader();
    	TestDataReader testDataReader = TestDataReader.getTestDataReader();    	
    	String itemName = testDataReader.getData(propertyReader.getProperty("environment") + "." + propertyName);
    	
    	input_search_term(itemName);
    	click_on_search_button_for_input_field();   	
    }
   
   public void input_search_term_by_reading_from_properties(String propertyName){
	   
	   String environment = Util.getEnvironment();
	   String countryGroup = (String) stateHolder.get("countryGroup");
	   
	   if(countryGroup.equalsIgnoreCase("PRICEBOOK")){
		   countryGroup = "pricebook"; 
	   }
	   else if(countryGroup.equalsIgnoreCase("NON-PRICEBOOK")){
		   countryGroup = "nonpricebook";
	   }

	   String searchTerm = "";
	   TestDataReader testDataReader = TestDataReader.getTestDataReader();
   	   if(testDataReader.hasProperty(environment + "." + countryGroup + ".country." + propertyName)){
		  searchTerm = testDataReader.getData(environment + "." + countryGroup + ".country." + propertyName);
	   }
   	   else{
   		   logger.error("'{}' property is not found in the test data properties file", environment + "." + countryGroup + ".country." + propertyName);
   		   throw new WebDriverException("'" + environment + "." + countryGroup + ".country." + propertyName + "' property is not found in the test data properties file");
   	   }
   	   
   	   input_search_term(searchTerm);
   }
   
   public void clickGenderLinkFromTopNav(String gender){
	   String url = driver.getCurrentUrl();
	   WebElement genderElement = Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(
			   By.xpath("//span[contains(@class, 'department-nav__text') and text() = '" + gender + "']")));
			   
	   genderElement.click();
       Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
       Util.waitLoadingBar(driver);
   }
}