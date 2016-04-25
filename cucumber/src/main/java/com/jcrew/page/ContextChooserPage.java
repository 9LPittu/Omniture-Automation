package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
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
    
    @FindBys({@FindBy(xpath=".//div[@class='context-chooser__column']/div/h5/i[not(@class='js-icon icon-see-more')]")})
    private List<WebElement> openedRegionalDrawers;
    
    @FindBy(xpath="//a[contains(@class,'js-start-shopping-button')]")
    private WebElement startShoppingButton;

    public ContextChooserPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    
    public boolean isInternationalContextChooserPageDisplayed() {
    	WebElement internationalContextChooser = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.id("page__international")));
        return internationalContextChooser.isDisplayed();
    }

    public boolean isRegionDisplayed(String region) {

    	WebElement internationalContextChooser = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.id("page__international")));
    	WebElement regionHeader = internationalContextChooser.findElement(By.xpath("//h5[text()='" + region + "']"));    	
    	return regionHeader.isDisplayed();
    }
    
    public boolean isAllRegionalDrawersClosedByDefault(){
    	return openedRegionalDrawers.size() == 0; 
    }
    
    public boolean isCountriesDisplayedCorrectlyUnderRegion(String region){
    	
    	WebElement internationalContextChooser = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.id("page__international")));
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
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("page__international")));
    	driver.findElement(By.id("page__international")).click();
    	WebElement link = driver.findElement(By.xpath("//p[@class='terms']/a[" + Util.xpathGetTextLower + "='" + linkName.toLowerCase() + "']"));    	
    	link.click();
    }
    
    public void clickButtonFromFAQSectionOnContextChooserPage(String buttonName){
    	WebElement button = driver.findElement(By.xpath("//section[@class='r-international__faq']/a[" + Util.xpathGetTextLower + "='" + buttonName.toLowerCase() +"']"));
    	button.click();
    }
    
    public void clickLinkFromFAQSectionOnContextChooserPage(String linkName){
    	WebElement link = driver.findElement(By.xpath("//section[@class='r-international__faq']/article/section/p/a[text()='borderfree.com']"));
    	link.click();
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

    	logger.info("Url after clicking on start shopping : {}", driver.getCurrentUrl());

    	
    	PropertyReader propertyReader = PropertyReader.getPropertyReader();
    	String url = propertyReader.getProperty("environment");
    	
    	Country country = (Country)stateHolder.get("context");

		String expectedURL = country.getHomeurl();

		Util.createWebDriverWait(driver).until(ExpectedConditions.urlMatches(expectedURL));
    	Util.waitLoadingBar(driver);
		logger.debug("expected url at this point should be "+driver.getCurrentUrl()+"  our expected url calculation {}", expectedURL);
    	return driver.getCurrentUrl().matches(expectedURL);
    }
    
    public void clickStartShoppingButton(){
    	
    	String selectedCountry = (String)stateHolder.get("selectedCountry");
    	
    	//For US country, page with 'START SHOPPING' button is not displayed
    	if(!selectedCountry.equalsIgnoreCase("UNITED STATES")){
    		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(startShoppingButton));
    		startShoppingButton.click();
    	}
    }
    
    public void selectTop10RandomCountry(){
	
		TestDataReader testDataReader = TestDataReader.getTestDataReader();
		String countryCodes = testDataReader.getData("CountryCodes");
		String[] arrCountryCodes = StringUtils.split(countryCodes, ",");	
		String countryCode = arrCountryCodes[Util.randomIndex(arrCountryCodes.length)];
		
		PropertyReader propertyReader = PropertyReader.getPropertyReader();
    	String url = propertyReader.getProperty("environment");
		
		Country country = new Country(url, countryCode);
		String currency = country.getCurrency();
		String countryName = country.getCountryName();
		String regionName = country.getRegion();
	
    	WebElement contextChooser = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("context-chooser__row")));   	
    	WebElement regionHeader = contextChooser.findElement(By.xpath("//h5[text()='" + regionName + "']"));
    	
    	//Click on region to show the countries listed    	
		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("page__international")));
		driver.findElement(By.id("page__international")).click();
		regionHeader.click();

    	//Click on country
		WebElement countryElement = Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(".//div[contains(@class,'accordian__wrap--context-chooser') "
				                                                                                                 + "and contains(@class,'is-expanded')]/ul/li/a/span[text()='" + countryName +"']"))));
    	countryElement.click();
    	

        stateHolder.put("context", country);
		
		logger.info("Selected country: {}", countryName);
    }
}