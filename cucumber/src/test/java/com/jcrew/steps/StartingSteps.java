package com.jcrew.steps;

import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class StartingSteps  {

    private DriverFactory driverFactory;
    private WebDriver driver;

    @Before
    public void setupDriver() throws IOException {
        driverFactory = new DriverFactory();
        driver = driverFactory.getDriver();
    }

    @Given("^User is on home page$")
    public void user_is_on_home_page() throws Throwable {
        PropertyReader reader = PropertyReader.getPropertyReader();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(reader.getHomePage());
    }

    @After
    public void quitDriver(Scenario scenario) throws IOException {

        if (scenario.isFailed()) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(scenario.getName() + ".png"));

        }

        driverFactory.destroyDriver();
    }

}
