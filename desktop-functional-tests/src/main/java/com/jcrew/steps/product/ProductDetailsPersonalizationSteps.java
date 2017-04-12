package com.jcrew.steps.product;

import com.jcrew.page.product.IPersonalization;
import com.jcrew.page.product.PersonalizationFactory;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Created by ngarcia on 4/4/17.
 */
public class ProductDetailsPersonalizationSteps extends DriverFactory {
    private IPersonalization personalization = PersonalizationFactory.getProductDetailsPersonalization(getDriver());

    @Then("Verify user can add monogram to product")
    public void is_monogramable() {
        assertTrue("User is able to add monogram", personalization.isMonogrameable());
    }

    @When("User adds monogram to product")
    public void add_monogram() {
        personalization.addMonogram();
    }
}
