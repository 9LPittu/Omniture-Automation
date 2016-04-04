package com.jcrew.steps;

import com.jcrew.page.MiniBag;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class MiniBagSteps extends DriverFactory {
    MiniBag miniBag = new MiniBag(getDriver());

    @Then("Verify mini bag contains (\\d+) item")
    public void verify_mini_bag_contains_x_item(int items) {
        if (items > 0 && items <= 3) {
            assertEquals("Mini bag contains expected" + items + " items", miniBag.getItemsNumber(), items);
        } else {
            assertEquals("Mini bag contains 3 items in stack", miniBag.getItemsNumber(), 3);
            assertTrue("Mini bag shows message to see more items", miniBag.showsMoreItems());
        }

    }

}
