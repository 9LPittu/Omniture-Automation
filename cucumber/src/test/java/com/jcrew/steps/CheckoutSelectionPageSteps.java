package com.jcrew.steps;

import com.jcrew.page.CheckoutSelectionPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutSelectionPageSteps extends DriverFactory {



    @When("^Selects to checkout as guest$")
    public void selects_to_checkout_as_guest() throws Throwable {
        new CheckoutSelectionPage(driver).selects_to_checkout_as_guest();

    }

}
