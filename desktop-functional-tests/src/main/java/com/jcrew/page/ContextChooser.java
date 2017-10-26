package com.jcrew.page;

import com.jcrew.page.header.HeaderWrap;
import com.jcrew.page.homepage.JCrewHomePage;
import com.jcrew.pojo.Country;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ContextChooser {

	private final WebDriver driver;
	private final Logger logger = LoggerFactory.getLogger(ContextChooser.class);
	private final StateHolder stateHolder = StateHolder.getInstance();
	private final WebDriverWait wait;

	@FindBy(xpath = "//a[contains(@class,'js-start-shopping-button')]")
	private WebElement startShoppingButton;
	@FindBy(id = "page__international")
	private WebElement internationalContextChooserPage;

	private HeaderWrap headerWrap;

	public ContextChooser(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		headerWrap = new HeaderWrap(driver);
		wait = Util.createWebDriverWait(driver);
		wait.until(ExpectedConditions.visibilityOf(internationalContextChooserPage));
	}

	public boolean isInternationalContextChooserPageDisplayed() {
		return internationalContextChooserPage.isDisplayed();
	}

	public boolean isRegionDisplayed(String region) {

		WebElement regionHeader = internationalContextChooserPage.findElement(By.xpath("//h5[normalize-space(text())='" + region + "']"));
		return regionHeader.isDisplayed();
	}


	public boolean isCountriesDisplayedCorrectlyUnderRegion(String region) {

		String propertyName = null;
		switch (region) {
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

		for (String country : arrCountries) {
			WebElement appCountry = null;
			boolean isCountryDisplayed = false;

			try {

				appCountry = driver.findElement(By.xpath("//div[contains(@class,'accordian__wrap--context-chooser')]"
						+ "/ul/li/a/span[@class='context-chooser__item--country' and "
						+ Util.xpathGetTextLower + "='" + country.toLowerCase() + "']"));

				isCountryDisplayed = appCountry.isDisplayed();
			} catch (NoSuchElementException nsee) {
				isCountryDisplayed = false;
			}

			if (isCountryDisplayed) {
				countriesDisplayed.add(country);
			} else {
				countriesMissing.add(country);
			}
		}

		if (countriesMissing.isEmpty()) {
			logger.info("For '{}' region, countries displayed are: {}", region, countriesDisplayed.toString());
		} else {
			logger.debug("For '{}' region, countries displayed are: {}", region, countriesDisplayed.toString());
			logger.error("For '{}' region, countries missing are: {}", region, countriesMissing.toString());
		}

		return countriesMissing.isEmpty();
	}

	public void clickLinkFromTermsSectionOnContextChooserPage(String linkName) {
		WebElement link = internationalContextChooserPage.findElement(By.xpath("//p[@class='terms']/a[" + Util.xpathGetTextLower + "='" + linkName.toLowerCase() + "']"));
		wait.until(ExpectedConditions.elementToBeClickable(link));
		
		Util.scrollAndClick(driver, link);
	}

	public void clickButtonFromFAQSectionOnContextChooserPage() {
		//WebElement button = internationalContextChooserPage.findElement(By.xpath(".//a[@class='r-international__faq--more']"));
		WebElement button = driver.findElement(By.xpath("*//article[@class='r-international__faq--list']/a[@class='r-international__faq--more']"));
       // button.click();
		JavascriptExecutor ex = (JavascriptExecutor)driver;
		ex.executeScript("arguments[0].click();", button);
	}

	public void clickLinkFromFAQSectionOnContextChooserPage() {
		WebElement link = internationalContextChooserPage.findElement(By.xpath(".//a[contains(text(),'borderfree.com')]"));
		Util.scrollAndClick(driver, link);
	}


	public boolean isUserOnCountrySpecificHomePage() {

		Country country = (Country) stateHolder.get("context");

		String expectedURL = country.getHomeurl();
		wait.until(ExpectedConditions.urlMatches(expectedURL));
		new JCrewHomePage(driver);
		logger.debug("expected url at this point should be " + expectedURL + " current url is " + driver.getCurrentUrl());
		return driver.getCurrentUrl().matches(expectedURL);
	}


	public void selectGroupRandomCountry(String country_group) {
		TestDataReader testData = TestDataReader.getTestDataReader();
		String selectedCountry = testData.getRandomCountry(country_group);
		
		selectCountryOnContextChooserPage(selectedCountry);
		
	}
	
	public void selectCountryOnContextChooserPage(String countryName){		
		PropertyReader propertyReader = PropertyReader.getPropertyReader();
		String url = propertyReader.getProperty("url");
		
		String currentUrl = driver.getCurrentUrl();
		
		//Click on country
		WebElement countryElement = internationalContextChooserPage.findElement(
                By.xpath(".//div[contains(@class,'accordian__wrap--context-chooser')]/ul/li/a[@data-country='" + countryName + "']"));
        wait.until(ExpectedConditions.visibilityOf(countryElement));
        JavascriptExecutor ex = (JavascriptExecutor)driver;
        ex.executeScript("arguments[0].click();", countryElement);
        //countryElement.click();
		Util.waitLoadingBar(driver);

		//Update Reader and create context
		Country country = new Country(url, countryName);
		stateHolder.put("context", country);

		TestDataReader reader = TestDataReader.getTestDataReader();
		reader.updateReader();
		
		logger.info("Selected country: {}", countryName);
		
		wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
		Util.waitForPageFullyLoaded(driver);
	}
}