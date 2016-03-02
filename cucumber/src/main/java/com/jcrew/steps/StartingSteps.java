package com.jcrew.steps;

import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import cucumber.api.java.BeforeStep;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class StartingSteps {

    private static final String TAKE_SCREENSHOT = "Screenshot";
    private final Logger logger = LoggerFactory.getLogger(StartingSteps.class);
    private final PropertyReader reader = PropertyReader.getPropertyReader();
    private final StateHolder stateHolder = StateHolder.getInstance();
    private DriverFactory driverFactory;
    private WebDriver driver;

    @Before
    public void setupDriver() throws IOException {
        driverFactory = new DriverFactory();
        driver = driverFactory.getDriver();
    }

    @Given("^User is on homepage$")
    public void user_is_on_home_page() {
        int retry = 0;
        boolean successfulLoad = false;
        while (retry < 2 && !successfulLoad) {
            try {
                getIntialPage();
              //  waitForPageToLoadUpToTheLastElementPriorScriptExecution();
                //Util.waitForPageFullyLoaded(driver);
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
                getTheJcrewGoldIntialPage(pageUrl);
                Util.waitForPageFullyLoaded(driver);
                successfulLoad = true;
            } catch (TimeoutException te) {
                logger.debug("Page did not load retry: {}", retry + 1);
                retry++;
            }
        }
    }

    @Given("^User is on Factory home page$")
    public void user_is_on_factory_home_page() {
        int retry = 0;
        boolean successfulLoad = false;
        while (retry < 2 && !successfulLoad) {
            try {
                getIntialPage();
                Util.waitForPageFullyLoaded(driver);
                successfulLoad = true;
            } catch (TimeoutException te) {
                logger.debug("Page did not load retry: {}", retry + 1);
                retry++;
            }
        }
    }

    @Given("^User is on Madewell home page$")
    public void user_is_on_madewell_home_page() {
        int retry = 0;
        boolean successfulLoad = false;
        while (retry < 2 && !successfulLoad) {
            try {
                getIntialPage();
                Util.waitForPageFullyLoaded(driver);
                successfulLoad = true;
            } catch (TimeoutException te) {
                logger.debug("Page did not load retry: {}", retry + 1);
                retry++;
            }
        }
    }


    private void waitForHeaderPromo(){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
    }

    private void waitForPageToLoadUpToTheLastElementPriorScriptExecution() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(
                By.className("footer__help__menu")));
    }

    public void getIntialPage() {
        String env = reader.getProperty("environment");
        String browser = reader.getProperty("browser");
        boolean isProdLikeEn = env.contains("aka-int-www")|| env.contains("argent")||env.contains("or");
        boolean isDesktop = browser.equals("firefox") || browser.equals("chrome");
        logger.debug("current url is: "+env);

        if(isProdLikeEn && isDesktop){
            logger.debug("Opening enable responsive page");
            driver.get(env + "/enableResponsive_sm.jsp");
            driver.findElement(By.linkText("click to browse")).click();
        } else {
            driver.get(env);
        }
    }

    public void getTheJcrewGoldIntialPage(String pageUrl){
        String env = reader.getProperty(pageUrl);
        logger.debug("current url is: "+env);
        driver.get(env);
    }

    @And("^User goes to homepage$")
    public void user_goes_to_homepage() throws Throwable {
        driver.get(reader.getProperty("environment"));
    }

    @And("^User bag is cleared$")
    public void user_bag_is_cleared() {
        driver.navigate().to(reader.getProperty("environment") + "/CleanPersistentCart.jsp");
        Util.waitForPageFullyLoaded(driver);
    }

    @After
    public void quitDriver(Scenario scenario) throws IOException {

        if (driver != null && (scenario.isFailed() || scenario.getName().contains(TAKE_SCREENSHOT))) {

            logger.debug("Taking screenshot of scenario {}", scenario.getName());

            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        }

        if (driverFactory != null) {
           driverFactory.destroyDriver();
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
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.embed(screenshot, "image/png");
                }

            } catch (Exception e) {
                logger.error("An exception happened when taking step screenshot", e);
            }
        }
    }
}
