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

    @FindBy(className = "js-primary-nav__link--search")
    private WebElement headerSearch;

    private TestDataReader testdataReader = TestDataReader.getTestDataReader();

    public HeaderSearch(WebDriver driver) {
        super(driver);
    }

    public void searchForSpecificTerm(String searchTerm) {
        String currentUrl = driver.getCurrentUrl();

        WebElement closeIcon = headerSearch.findElement(
                By.xpath(".//span[contains(@class,'icon-close js-primary-nav__search__button--clear')]"));

        if (closeIcon.isDisplayed()) {
            closeIcon.click();
        } else {
            headerSearch.click();
        }

        WebElement searchInput = headerSearch.findElement(
                By.xpath(".//input[contains(@class,'js-primary-nav__input--search')]"));

        searchInput.clear();
        searchInput.sendKeys(searchTerm);
        searchInput.sendKeys(Keys.ENTER);

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
