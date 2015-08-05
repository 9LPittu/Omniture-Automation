package com.jcrew.steps;

import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class StartingSteps {

    private DriverFactory driverFactory;
    private WebDriver driver;

    @Before
    public void setupDriver() throws IOException {
        driverFactory = new DriverFactory();
        driver = driverFactory.getDriver();
    }

    @Given("^User is on homepage$")
    public void user_is_on_home_page() throws Throwable {
        PropertyReader reader = PropertyReader.getPropertyReader();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(reader.getHomePage());
    }

    @After
    public void quitDriver(Scenario scenario) throws IOException {

        if (driver != null && scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        }

        if (driverFactory != null) {
            driverFactory.destroyDriver();
        }
    }

}
