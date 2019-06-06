package com.jcrew.page.header;

import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by ngarcia on 3/2/17.
 */
@SuppressWarnings("unused")
public class HeaderSearch extends HeaderWrap {

    @FindBy(className = "nc-nav__search__input")
    private WebElement headerSearch;
    
    @FindBy(xpath = ".//span[contains(@class,'icon-close js-primary-nav__search__button--clear')]")
    private WebElement closeIcon;
    
    @FindBy(xpath = "//span[contains(@class,'primary-nav__text primary-nav__text--search')]")
    private WebElement headerSearchFactory;
    
    @FindBy(className = "js-primary-nav__input--search")
    private WebElement searchTextFactory;
    
    

    private TestDataReader testdataReader = TestDataReader.getTestDataReader();

    public HeaderSearch(WebDriver driver) {
        super(driver);
    }

	public void searchForSpecificTerm(String searchTerm) {
		String currentUrl = driver.getCurrentUrl();
		if(currentUrl.contains("factory")) {
        	headerSearchFactory.click();
        	//headerSearchFactory.clear();
        	searchTextFactory.sendKeys(searchTerm);
        	searchTextFactory.sendKeys(Keys.ENTER);
        }else {
        	headerSearch.click();
        	headerSearch.clear();
            headerSearch.sendKeys(searchTerm);
            headerSearch.sendKeys(Keys.ENTER);
        }
		logger.info("Searching for {}", searchTerm);
		wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
		Util.waitLoadingBar(driver);
	}

    public void searchFor(String searchItem) {
        PropertyReader propertyReader = PropertyReader.getPropertyReader();
        String env = propertyReader.getProperty("environment");

        if (testdataReader.hasProperty(searchItem)) {
            searchItem = testdataReader.getData(searchItem);
        }

        searchForSpecificTerm(searchItem);
    }

    public String getSearchDrawerState() {
        WebElement searchInput = headerSearch.findElement(
                By.xpath(".//input[contains(@class,'js-primary-nav__input--search')]"));

        if(searchInput.isDisplayed()) {
            return "open";
        } else {
            return "closed";
        }
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.visibilityOf(headerSearch));
        headerSearch.click();
    }
}
