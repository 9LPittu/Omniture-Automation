package com.jcrew.steps;

import com.jcrew.page.ShoppingBagPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShoppingBagSteps extends DriverFactory {


    @When("^Wants to checkout$")
    public void wants_to_checkout_the_product() throws Throwable {
        new ShoppingBagPage(getDriver()).wants_to_checkout();
    }
}
