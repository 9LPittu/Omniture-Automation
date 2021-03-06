package com.jcrew.steps;

import com.jcrew.pojo.Country;
import com.jcrew.util.*;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.BeforeStep;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class StartingSteps {

    private final Logger logger = LoggerFactory.getLogger(StartingSteps.class);
    private final PropertyReader reader = PropertyReader.getPropertyReader();
    private final StateHolder stateHolder = StateHolder.getInstance();
    private DriverFactory driverFactory;
    private WebDriver driver;


    @Before
    public void setupDriver() throws IOException {
        stateHolder.put("deletecookies", false);
        driverFactory = new DriverFactory();
        driver = driverFactory.getDriver();
    }

    @Given("User is on homepage with clean session")
    public void user_is_on_home_page_with_clean_session() {
        driverFactory.deleteBrowserCookies();
        user_is_on_home_page();
    }

    @Given("User navigates to ([^\"]*) with clean session")
    public void user_navigates_with_clean_session(String extensionUrl) {

        String country = reader.getProperty("country");

        TestDataReader testData = TestDataReader.getTestDataReader();
        testData.updateReader(country);

        switch (extensionUrl.toLowerCase().trim()) {
            case "category page":
                extensionUrl =  testData.getData("url.category");
                break;
            case "pdp":
                extensionUrl =  testData.getData("url.pdp");
                break;
            default:
                break;
        }

        String env = reader.getProperty("url");

        Country context = new Country(env, country);
        stateHolder.put("context", context);
        env = context.getHomeurl();

        driverFactory.deleteBrowserCookies();
        driver.get(env + extensionUrl);
        Util.waitLoadingBar(driver);
    }

    @Given("User is on clean session international ([^\"]*) page$")
    public void user_goes_to_international_page(String country_group, List<String> pageUrlList) throws Throwable {

        TestDataReader testData = TestDataReader.getTestDataReader();
        String page = pageUrlList.get(Util.randomIndex(pageUrlList.size()));
        page = page.toLowerCase();

        String pageURL = testData.getData("url." + page);
        stateHolder.put("pageUrl", pageURL);

        int i = 0;
        while (i <= 2) {
            try {
                driverFactory.deleteBrowserCookies();
                getInternationalUrl(testData.getCountry(country_group), pageURL);
                Util.createWebDriverWait(driver, Util.getDefaultTimeOutValue() / 3).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class,'c-header__welcomemat--button')]")));
                break;
            } catch (Exception e) {
                i++;
                if (i > 2) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Given("User is on clean session in ([^\"]*) homepage page$")
    public void user_goes_to_international_homepage(String country_group) throws Throwable {
        TestDataReader testData = TestDataReader.getTestDataReader();

        int i = 0;
        while(i<=2){
        	try{
        		driverFactory.deleteBrowserCookies();
        		getInternationalUrl(testData.getCountry(country_group), "");
        		Util.createWebDriverWait(driver, Util.getDefaultTimeOutValue()/3).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class,'c-header__welcomemat--button')]")));
        		stateHolder.put("countryGroup", country_group);
        		break;
        	}
        	catch(Exception e){
        		i++;        		
        		if(i>2){
        			e.printStackTrace();
        		}
        		
        		driverFactory.destroyDriver();
        		driver = driverFactory.getDriver();
        	}
        }
    }

    @Given("^User is on international homepage$")
    public void user_goes_to_international_homepage() {
        Country c = (Country) stateHolder.get("context");
        driver.get(c.getHomeurl());
    }

    public void getInternationalUrl(String selectedCountry, String pageURL) {
        String env = reader.getProperty("url");
        
		//Update Reader and create context
        TestDataReader testData = TestDataReader.getTestDataReader();
        testData.updateReader(selectedCountry);
        Country countrydetails = new Country(env, selectedCountry);

        stateHolder.put("context", countrydetails);
        logger.debug("country selected {}", countrydetails.getCountryName());

        String selectedCountryHomeUrl = countrydetails.getHomeurl();
        env = selectedCountryHomeUrl + pageURL;
        stateHolder.put("randomUrl", env);

        boolean isProdLikeEn = env.contains("aka-int-www") || env.contains("argent");

        if (!isProdLikeEn && reader.isSystemPropertyTrue("force.cache")) {
            logger.debug("Forcing Akamai cache");
            UUID uuid = UUID.randomUUID();
            env = env + "?c=" + uuid.toString();
        }

        driver.get(env);
        Util.waitLoadingBar(driver);
    }

    @Given("^User is on homepage$")
    public void user_is_on_home_page() {
        int retry = 0;
        boolean successfulLoad = false;
        while (retry < 2 && !successfulLoad) {
            try {
                getInitialPage();
                waitForHeaderPromo();
                successfulLoad = true;
            } catch (TimeoutException te) {
                logger.debug("Page did not load retry: {}", retry + 1);
                retry++;
            }
        }
    }

    @And("^User is on homepage with siteid$")
    public void user_goes_to_homepage_with_siteid() {
        int retry = 0;
        boolean successfulLoad = false;

        String env = reader.getProperty("url");

        Country context = new Country(env, reader.getProperty("country"));
        env = context.getHomeurl() + "?siteId=asdfsadf&srcCode=asdfsadf";
        stateHolder.put("context", context);

        logger.debug("current url is: " + env);

        while (retry < 2 && !successfulLoad) {
            try {
                driver.get(env);
                waitForHeaderPromo();
                successfulLoad = true;
            } catch (TimeoutException te) {
                logger.debug("Page did not load retry: {}", retry + 1);
                retry++;
            }
        }
    }

    private void waitForHeaderPromo() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
    }

    private void waitForPageToLoadUpToTheLastElementPriorScriptExecution() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(
                By.className("footer__help__menu")));
    }

    public void getInitialPage() {
        String country = reader.getProperty("country");
        String env = reader.getProperty("url");
        String browser = reader.getProperty("browser");
        
        TestDataReader testData = TestDataReader.getTestDataReader();
        testData.updateReader(country);
        Country context = new Country(env, country);
        stateHolder.put("context", context);
        env = context.getHomeurl();

        boolean isProdLikeEn = env.contains("argent");
        boolean isDesktop = browser.equals("firefox") || browser.equals("chrome");
        logger.debug("current url is: " + env);

        if (isProdLikeEn && isDesktop) {
            logger.debug("Opening enable responsive page");
            driver.get(env + "/enableResponsive_sm.jsp");
            driver.findElement(By.linkText("click to browse")).click();
        } else {

            if (reader.isSystemPropertyTrue("force.cache")) {
                logger.debug("Forcing Akamai cache");
                UUID uuid = UUID.randomUUID();
                env = env + "?c=" + uuid.toString();
            }

            driver.get(env);
        }
    }

    @And("user should see country code in the url for international countries")
    public void user_should_see_country_code_in_url_for_international_countries() {
        Country c = (Country) stateHolder.get("context");
        assertTrue("Country code '" + c.getCountry() + "' should be displayed in the url except United States",
                Util.createWebDriverWait(driver).until(ExpectedConditions.urlMatches(c.getHomeurl())));
    }

    @And("^User goes to homepage$")
    public void user_goes_to_homepage() throws Throwable {
        driver.get(reader.getProperty("url"));
    }


    @And("^User bag is cleared$")
    public void user_bag_is_cleared() {
        driver.navigate().to(reader.getProperty("url") + "/CleanPersistentCart.jsp");
        Util.waitForPageFullyLoaded(driver);
    }

    @And("^Deletes browser cookies$")
    public void deletes_browser_cookies() {
        driverFactory.deleteBrowserCookies();
        stateHolder.put("deletecookies", true);
    }

    @BeforeStep
    public void beforeStep(Scenario s) {
    }

    @And("^user appends ([^\"]*) to the url in the browser and navigate to the page$")
    public void user_appends_and_navigates_to_url(String urlParam) {
        PropertyReader reader = PropertyReader.getPropertyReader();
        if (reader.getProperty("environment").equalsIgnoreCase("qa2")) {
            driver.navigate().to(driver.getCurrentUrl() + urlParam);
        }
    }

    @When("user navigates to random page from below list$")
    public void user_navigates_to_random_page(List<String> pageUrlList) throws Throwable {
        TestDataReader testData = TestDataReader.getTestDataReader();
        String page = pageUrlList.get(Util.randomIndex(pageUrlList.size()));
        page = page.toLowerCase();
        String pageURL = testData.getData("url." + page);
        logger.info("Randomly selected url is: {}", pageURL);

        String env = reader.getProperty("url");

        driver.get(env + pageURL);
        Util.waitLoadingBar(driver);
    }
}