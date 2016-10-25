package com.jcrew.steps;

import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.util.*;

import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.SAccountReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
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

    private static final String TAKE_SCREENSHOT = "Screenshot";
    private final Logger logger = LoggerFactory.getLogger(StartingSteps.class);
    private final PropertyReader reader = PropertyReader.getPropertyReader();
    private final SAccountReader saccountreader = SAccountReader.getPropertyReader();
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

    @Given("^User is on ([^\"]*) home page$")
    public void user_is_on_jcrew_gold_home_page(String pageUrl) {
        int retry = 0;
        boolean successfulLoad = false;
        while (retry < 2 && !successfulLoad) {
            try {
                getTheInitialPage(pageUrl);
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

    public void getTheInitialPage(String pageUrl) {
        String env = saccountreader.getProperty(pageUrl);
        logger.debug("current url is: " + env);
        driver.get(env);
        String strTitle = saccountreader.getProperty("title." + pageUrl);
        Util.createWebDriverWait(driver).until(ExpectedConditions.titleContains(strTitle));
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

    @After
    public void quitDriver(Scenario scenario) {
    	 String data;

        if (driver != null && (scenario.isFailed() || scenario.getName().contains(TAKE_SCREENSHOT))) {
            logger.debug("Taking screenshot of scenario {}", scenario.getName());
            try {
                final byte[] screenshot = ScreenshotGenerator.getScreenshot(driver);
                scenario.embed(screenshot, "image/png");

                String log = Util.logBrowserErrors(driver);
                scenario.embed(log.getBytes(), "text/plain");

                data = getProducts();
                if (!data.isEmpty()) {
                    scenario.embed(data.getBytes(), "text/plain");
                }
                
                deletes_browser_cookies();
            } catch (RuntimeException e) {
                logger.error("An exception happened when taking step screenshot after scenario", e);
                driverFactory.resetDriver();
            }
        }

        if (driverFactory != null && (boolean) stateHolder.get("deletecookies")) {
            driverFactory.destroyDriver();
        }

        PropertyReader reader = PropertyReader.getPropertyReader();
        if (!reader.getProperty("environment").equalsIgnoreCase("ci") && stateHolder.hasKey("sidecarusername")) {
            try {
                UsersHub userHub = UsersHub.getInstance();
                userHub.releaseUserCredentials();
            } catch (Exception e) {
                logger.error("Failed to release user '{}' in DB!!!", (String) stateHolder.get("sidecarusername"));
            }
        }

        stateHolder.clear();
    }

    @BeforeStep
    public void beforeStep(Scenario s) {
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (!scenario.isFailed()) {
            try {

                if (driver != null && "true".equalsIgnoreCase(System.getProperty("take.step.screenshot"))) {
                    byte[] screenshot = ScreenshotGenerator.getScreenshot(driver);
                    scenario.embed(screenshot, "image/png");
                }

            } catch (Exception e) {
                logger.error("An exception happened when taking step screenshot after step", e);
            }
        }
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
    
    private String getProducts() {
        String products = "";

        if (stateHolder.hasKey("toBag")) {
            List<Product> inBag = stateHolder.getList("toBag");
            for (Product c : inBag) {
                products += c.getProductName() + "\t" +
                        c.getProductCode() + "\t" +
                        c.getSelectedColor() + "\t" +
                        c.getSelectedSize() + "\t" +
                        c.getPriceList() + "\n";
            }

            if (!products.isEmpty()) {
                products = "PRODUCT NAME\tITEM NUMBER\tCOLOR\tSIZE\tPRICE\n" + products;
            }
        }

        return products;
    }
}