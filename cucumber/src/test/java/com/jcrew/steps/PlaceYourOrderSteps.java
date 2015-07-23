package com.jcrew.steps;

import com.jcrew.page.PlaceYourOrderPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertFalse;

public class PlaceYourOrderSteps extends DriverFactory {

    @Then("^User places its order$")
    public void user_places_its_order() throws Throwable {

        PlaceYourOrderPage placeYourOrderPage = new PlaceYourOrderPage(driver);

        placeYourOrderPage.user_places_its_order();

        assertFalse("An error message should not be displayed as the billing values are fake",
                placeYourOrderPage.containsErrors());

    }

}
