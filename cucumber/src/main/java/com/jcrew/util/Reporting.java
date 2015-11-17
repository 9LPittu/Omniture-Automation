package com.jcrew.util;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;

public class Reporting {

<<<<<<< HEAD
    public void takeScreenshot(Scenario scenario) {

        try {
            final PropertyReader propertyReader = PropertyReader.getPropertyReader();
            WebDriver driver = new DriverFactory().getDriver();
            if ("yes".equalsIgnoreCase(propertyReader.getScreenshotForEveryStep())) {
               byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
=======
//    public void takeScreenshot(Scenario scenario) {
//
//        try {
//            final PropertyReader propertyReader = PropertyReader.getPropertyReader();
//            WebDriver driver = new DriverFactory().getDriver();
//            if ("yes".equalsIgnoreCase(propertyReader.getScreenshotForEveryStep())) {
//                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//                scenario.embed(screenshot, "image/png");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
>>>>>>> d9180584514138d143933edf756dc71ec54b56fb
}