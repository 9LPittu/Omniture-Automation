package com.jcrew.steps;

import com.jcrew.page.PlaceYourOrderPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;

public class PlaceYourOrderSteps extends DriverFactory {

    @Then("^User places its order$")
    public void user_places_its_order() throws Throwable {

        PlaceYourOrderPage placeYourOrderPage = new PlaceYourOrderPage(getDriver());

        placeYourOrderPage.user_places_its_order();


    }

}
