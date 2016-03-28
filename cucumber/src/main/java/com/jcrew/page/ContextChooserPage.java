package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    	
    	if(isMobileView()){
    		regionHeader.click();
    	}
    	
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
    			if(isMobileView()){
		    		appCountry = driver.findElement(By.xpath("//div[contains(@class,'accordian__wrap--context-chooser') and contains(@class,'is-expanded')]"
				                                              + "/ul/li/a/span[@class='context-chooser__item--country' and "
				                                              + Util.xpathGetTextLower + "='" + country.toLowerCase() + "']"));
    			}
    			else{
    				appCountry = driver.findElement(By.xpath("//div[contains(@class,'accordian__wrap--context-chooser')]"
                            								  + "/ul/li/a/span[@class='context-chooser__item--country' and "
                                                              + Util.xpathGetTextLower + "='" + country.toLowerCase() + "']"));
    			}
	    		
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
    	
    	boolean isDrawerOpened = false;
    	if(isMobileView()){
    		isDrawerOpened = driver.findElement(By.xpath("//div[@class='context-chooser__column']/div/h5[text()='" + region  + "']/i[@class='js-icon icon-see-less']")).isDisplayed();
    	}
    	else{
    		isDrawerOpened = true;
    	}
    	
    	return countriesMissing.isEmpty() && isDrawerOpened;
    }
    
    public void clickLinkFromTermsSectionOnContextChooserPage(String linkName){
    	driver.findElement(By.id("page__international")).click();
    	WebElement link = driver.findElement(By.xpath("//p[@class='terms']/a[" + Util.xpathGetTextLower + "='" + linkName.toLowerCase() + "']"));    	
    	link.click();
    }
    
    public void clickButtonFromFAQSectionOnContextChooserPage(String buttonName){
    	WebElement button = driver.findElement(By.xpath("//section[@class='r-international__faq']/a[" + Util.xpathGetTextLower + "='" + buttonName.toLowerCase() +"']"));
    	button.click();
    }
    
    public void selectRandomCountry(){
    	
    	driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    	WebElement internationalContextChooser = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.id("page__international")));
    	List<WebElement> regionHeaders = internationalContextChooser.findElements(By.tagName("h5"));   	
    	int randomIndex = Util.randomIndex(regionHeaders.size());
    	String regionName = regionHeaders.get(randomIndex).getText();
    	logger.info("Region name: {}", regionName);
    	
    	WebElement regionHeader = internationalContextChooser.findElement(By.xpath("//h5[text()='" + regionName + "']"));
    	
    	if(isMobileView()){
    		driver.findElement(By.id("page__international")).click();
    		regionHeader.click();
    	}
    	
    	List<WebElement> countries = driver.findElements(By.xpath(".//div[contains(@class,'accordian__wrap--context-chooser') and contains(@class,'is-expanded')]/ul/li/a/span"));
    	int randomNum = Util.randomIndex(countries.size());
    	WebElement country = countries.get(randomNum);
    	String countryName = country.getText();
    	country.click();
		stateHolder.put("selectedCountry", countryName);
		logger.info("Selected country: {}", countryName);
    }
    
    public boolean isMobileView(){
    	
    	PropertyReader propertyReader = PropertyReader.getPropertyReader();
    	String browser = propertyReader.getProperty("browser");
    	
    	boolean isResponsiveSite = true;
    	if(propertyReader.hasProperty("window.width")){
    		isResponsiveSite = Integer.parseInt(propertyReader.getProperty("window.width")) <= 400;
    	}
    	
    	return (browser.equalsIgnoreCase("androidchrome") || browser.equalsIgnoreCase("iossafari") || browser.equalsIgnoreCase("phantomjs")) || isResponsiveSite;
    }
    
    public boolean isUserOnCountrySpecificHomePage(){
    	
    	String selectedCountry = (String)stateHolder.get("selectedCountry");
    	
    	PropertyReader propertyReader = PropertyReader.getPropertyReader();
    	String url = propertyReader.getProperty("environment");
    	
    	Country country = new Country(selectedCountry);    	
    	String countryCode = country.getCountryCode();
    	
    	Util.createWebDriverWait(driver).until(ExpectedConditions.urlMatches(url + "/" + countryCode + "/"));
    	return driver.getCurrentUrl().matches(url + "/" + countryCode + "/");    	
    }
}