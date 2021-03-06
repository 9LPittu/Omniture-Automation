package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.Util;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextChooserPage {

    private final WebDriver driver;    
    private final Logger logger = LoggerFactory.getLogger(ContextChooserPage.class);    
    private final StateHolder stateHolder = StateHolder.getInstance();
    
    @FindBys({@FindBy(xpath="//div[@class='context-chooser__column']/div/h5/i[not(@class='js-icon icon-see-more')]")})
    private List<WebElement> openedRegionalDrawers;
    
    @FindBy(xpath="//a[contains(@class,'js-start-shopping-button')]")
    private WebElement startShoppingButton;

    public ContextChooserPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    
    public boolean isInternationalContextChooserPageDisplayed() {
    	WebElement internationalContextChooser = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='page__international']")));
        return internationalContextChooser.isDisplayed();
    }

    public boolean isRegionDisplayed(String region) {

    	WebElement internationalContextChooser = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='page__international']")));
    	WebElement regionHeader = internationalContextChooser.findElement(By.xpath("//h5[text()='" + region + "']"));    	
    	return regionHeader.isDisplayed();
    }
    
    public boolean isAllRegionalDrawersClosedByDefault(){
    	return openedRegionalDrawers.size() == 0; 
    }
    
    public boolean isCountriesDisplayedCorrectlyUnderRegion(String region){
    	
    	WebElement internationalContextChooser = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='page__international']")));
    	WebElement regionHeader = internationalContextChooser.findElement(By.xpath("//h5[text()='" + region + "']"));   	
		regionHeader.click();
    	
    	String propertyName = null;
    	switch(region){
	    	case "UNITED STATES & CANADA":
	    		propertyName = "UNITED_STATES_AND_CANADA_REGION_COUNTRIES";
	    		break;
	    	case "ASIA PACIFIC":
	    		propertyName = "ASIA_PACIFIC_REGION_COUNTRIES";
	    		break;
	    	case "EUROPE":
	    		propertyName = "EUROPE_REGION_COUNTRIES";
	    		break;
	    	case "LATIN AMERICA & THE CARIBBEAN":
	    		propertyName = "LATIN_AMERICA_AND_THE_CARIBBEAN_REGION_COUNTRIES";
	    		break;
	    	case "MIDDLE EAST & AFRICA":
	    		propertyName = "MIDDLE_EAST_AND_AFRICA_REGION_COUNTRIES";
	    		break;
    	}
    	
    	TestDataReader dataReader = TestDataReader.getTestDataReader();
    	String countries = dataReader.getData(propertyName);
    	String[] arrCountries = countries.split(";");
    	
    	List<String> countriesDisplayed = new ArrayList<String>();
    	List<String> countriesMissing = new ArrayList<String>();
    	
    	for(String country:arrCountries){
    		WebElement appCountry = null;
    		boolean isCountryDisplayed = false;
    		
    		try{
    			
	    		appCountry = driver.findElement(By.xpath("//div[contains(@class,'accordian__wrap--context-chooser') and contains(@class,'is-expanded')]"
			                                              + "/ul/li/a/span[@class='context-chooser__item--country' and "
			                                              + Util.xpathGetTextLower + "='" + country.toLowerCase() + "']"));
	    		
	    		isCountryDisplayed = appCountry.isDisplayed();
    		}
    		catch(NoSuchElementException nsee){
    			isCountryDisplayed = false;
    		}
    		
    		if(isCountryDisplayed){
    			countriesDisplayed.add(country);
    		}
    		else{
    			countriesMissing.add(country);
    		}
    	}
    	
    	if(countriesMissing.isEmpty()){
    		logger.info("For '{}' region, countries displayed are: {}", region, countriesDisplayed.toString());
    	}
    	else{
    		logger.debug("For '{}' region, countries displayed are: {}", region, countriesDisplayed.toString());
    		logger.error("For '{}' region, countries missing are: {}", region, countriesMissing.toString());
    	}
    	
    	boolean isDrawerOpened = driver.findElement(By.xpath("//div[@class='context-chooser__column']/div/h5[text()='" + region  + "']/i[@class='js-icon icon-see-less']")).isDisplayed();
    	
    	return countriesMissing.isEmpty() && isDrawerOpened;
    }
    
    public void clickLinkFromTermsSectionOnContextChooserPage(String linkName){
    	WebElement element = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='page__international']")));
    	element.click();
    	
    	PropertyReader propertyReader = PropertyReader.getPropertyReader();
        String browser = propertyReader.getProperty("browser");
        if(browser.equalsIgnoreCase("firefox")||browser.equalsIgnoreCase("chrome")){
        	//Adding this piece of code as terms of use link click is not working in firefox & chrome
        	driver.navigate().refresh();
        	Util.waitLoadingBar(driver);
        }
    	
    	WebElement link = Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(
    					  driver.findElement(By.xpath(
    				      "//p[@class='terms']/a[" + Util.xpathGetTextLower + "='" + linkName.toLowerCase() + "']"))));
    	
    	try{
    		link.click();
    	}
    	catch(WebDriverException wde){
    		Util.scrollAndClick(driver, link);
    	}
    	
    	Util.waitLoadingBar(driver);
    }
    
    public void clickButtonFromFAQSectionOnContextChooserPage(String buttonName){
    	WebElement button = Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(
    						driver.findElement(By.xpath(
    					    "//section[@class='r-international__faq']/a[" + Util.xpathGetTextLower + "='" + buttonName.toLowerCase() +"']"))));
    	button.click();
    }
    
    public void clickLinkFromFAQSectionOnContextChooserPage(String linkName){
    	WebElement link = Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(
    			          driver.findElement(By.xpath(
    			          "//section[@class='r-international__faq']/article/section/p/a[text()='" + linkName + "']"))));
    	link.click();
    	Util.waitLoadingBar(driver);
    }
    
    public void selectRandomCountry()  {

		WebElement contextChooser = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("context-chooser__row")));
    	List<WebElement> regionHeaders = contextChooser.findElements(By.tagName("h5"));   	
    	int randomIndex = Util.randomIndex(regionHeaders.size());
    	String regionName = regionHeaders.get(randomIndex).getText();
    	logger.info("Region name: {}", regionName);

    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("page__international")));
		driver.findElement(By.id("page__international")).click();
    	WebElement regionHeader = contextChooser.findElement(By.xpath("//h5[text()='" + regionName + "']"));
		regionHeader.click();
    	
    	List<WebElement> countries = driver.findElements(By.xpath(".//div[contains(@class,'accordian__wrap--context-chooser') and contains(@class,'is-expanded')]/ul/li/a/span"));
    	
    	String countryName = "";
    	WebElement country = null;
    	
    	//For France, application is navigating to jsp page. So, excluding France country selection
    	while(countryName.isEmpty() || countryName.equalsIgnoreCase("FRANCE")){
    		country = countries.get(Util.randomIndex(countries.size()));
    		countryName = country.getText();
    	}
    	
    	country.click();
		stateHolder.put("selectedCountry", countryName);
		logger.info("Selected country: {}", countryName);
    }
    
    public boolean isUserOnCountrySpecificHomePage() {
    	try{
    		logger.info("Url after clicking on start shopping : {}", driver.getCurrentUrl());
			
    		Country country = (Country)stateHolder.get("context");
			String expectedURL = country.getHomeurl();
	
			Util.createWebDriverWait(driver).until(ExpectedConditions.urlMatches(expectedURL));
	    	Util.waitLoadingBar(driver);
			logger.debug("expected url at this point should be " + driver.getCurrentUrl() + "  our expected url calculation {}", expectedURL);
	    	return driver.getCurrentUrl().matches(expectedURL);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public void clickStartShoppingButton(){
    	
    	String selectedCountry = (String)stateHolder.get("selectedCountry");
    	
    	//For US country, page with 'START SHOPPING' button is not displayed
    	if(!selectedCountry.equalsIgnoreCase("UNITED STATES")){
    		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(startShoppingButton));
    		startShoppingButton.click();
    	}
    }
    
    public void selectGroupRandomCountry(String country_group) {
		
    	PropertyReader propertyReader = PropertyReader.getPropertyReader();
    	String url = propertyReader.getProperty("url");
    	
		TestDataReader testData = TestDataReader.getTestDataReader();
		String selectedCountry = testData.getRandomCountry(country_group.trim());
		
		//Update Reader and create context
		testData.updateReader(selectedCountry);
		Country country = new Country(url, selectedCountry);
		stateHolder.put("context", country);
		
		selectCountryOnContextChooserPage(selectedCountry);		
		
		logger.info("Selected country: {}", country.getCountryName());
    }
    
    public void selectCountryOnContextChooserPage(String countryCode){    	
    	
    	Country country = (Country) stateHolder.get("context");
		String countryName = country.getCountryName();
		String regionName = country.getRegion();
		
		Util.waitLoadingBar(driver);
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("context-chooser__row")));  	
    	
    	//Click on region to display the countries listed    	
		int i = 0;
		while(i<=2){
			try{
	             WebElement regionHeader = Util.createWebDriverWait(driver,Util.getDefaultTimeOutValue()/3).until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='" + regionName + "']")));
				 regionHeader.click();
				 Util.createWebDriverWait(driver,5).until(ExpectedConditions.visibilityOfElementLocated(
						  By.xpath("//div[contains(@class,'accordian__wrap--context-chooser') and contains(@class,'is-expanded')]")));
				break;
			}
			catch(Exception e){
				i++;
			}
		}	

    	//Click on country
		Util.waitForPageFullyLoaded(driver);
		Util.waitLoadingBar(driver);
		
		String currentUrl = driver.getCurrentUrl();
		i = 0;
		while(i<=1){
			try{
				WebElement countryElement = Util.createWebDriverWait(driver).until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'accordian__wrap--context-chooser') "
						                                                     + "and contains(@class,'is-expanded')]/ul/li/a/span[text()='" 
						                                                     + countryName +"']")));
				Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(countryElement));
		    	countryElement.click();
		    	Util.createWebDriverWait(driver,Util.getDefaultTimeOutValue()/2).until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
		    	Util.waitLoadingBar(driver);
		    	break;
			}
			catch(Exception e){
				i++;
				if(i>1){
					e.printStackTrace();
				}
			}
		}
    }
    
    public String[] getInternationalCountriesArray(){
    	TestDataReader testData = TestDataReader.getTestDataReader();
    	
    	String pricebookCountries = testData.getData("pricebookCountries");
    	String nonPricebookCountries = testData.getData("nonPricebookCountries");
    	String internationalCountries = pricebookCountries + ";" + nonPricebookCountries;    	
    	
    	return internationalCountries.split(";");
    }
    
    public void selectRandomInternationalCountry(){
		
    	String internationalCountriesArray[] = getInternationalCountriesArray();
    		
		int countryIndex = Util.randomIndex(internationalCountriesArray.length);
		String selectedCountry = internationalCountriesArray[countryIndex].toLowerCase();
		
		//Update Reader and create context
		TestDataReader testData = TestDataReader.getTestDataReader();
		testData.updateReader(selectedCountry);
		
		PropertyReader propertyReader = PropertyReader.getPropertyReader();
		String url = propertyReader.getProperty("url");
		Country country = new Country(url, selectedCountry);
		
		stateHolder.put("context", country);
		
		selectCountryOnContextChooserPage(selectedCountry);
    }
    
public void selectPassedInternationalCountry(String selectedCountry){
	TestDataReader testData = TestDataReader.getTestDataReader();
	PropertyReader propertyReader = PropertyReader.getPropertyReader();
	String url = propertyReader.getProperty("url");
	//Update Reader and create context
			testData.updateReader(selectedCountry);
			Country country = new Country(url, selectedCountry);
			stateHolder.put("context", country);
			selectCountryOnContextChooserPage(selectedCountry);		
			logger.info("Selected country: {}", country.getCountryName());
		
    }
}
