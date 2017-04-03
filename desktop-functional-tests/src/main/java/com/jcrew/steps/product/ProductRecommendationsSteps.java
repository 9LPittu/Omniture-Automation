package com.jcrew.steps.product;

import com.jcrew.page.product.ProductRecommendations;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Created by ngarcia on 3/28/17.
 */
public class ProductRecommendationsSteps extends DriverFactory {
    private ProductRecommendations pdp = new ProductRecommendations(getDriver());

    @Then("Verify that product contains no more than 9 recommendations")
    public void has_9_recommended_products() {
        assertTrue("Product has no more than 9 recommendations", pdp.getRecommendationsNumber() <= 9);
    }

    @When("User clicks on first recommended product")
    public void first_recommended_product() {
        if (pdp.getRecommendationsNumber() > 0) {
            pdp.clickFirstRecommendedProduct();
        } else {
            pdp.logger.warn("This product does not have recommendation. Ignoring step.");
        }
    }

    @Then("Verify product has recommended products")
    public void has_recommended_products() {
        assertTrue("Product has recommendations", pdp.getRecommendationsNumber() > 0);
    }

    @When("User clicks on any recommended product")
    public void any_recommended_product() {
        if (pdp.getRecommendationsNumber() > 0) {
            pdp.clickRandomRecommendedProduct();
        } else {
            pdp.logger.warn("This product does not have recommendation. Ignoring step.");
        }
    }

}
