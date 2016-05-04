package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;


public class ReviewSteps extends DriverFactory {
	
	private final OrderReview reviewPage = new OrderReview(getDriver());

    @Then("^Clicks on place your order$")
    public void user_places_its_order() throws Throwable {
        reviewPage.user_places_its_order();
    }
}