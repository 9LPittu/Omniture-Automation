package com.jcrew.steps.header;

import com.jcrew.page.header.HeaderBag;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;

/**
 * Created by ngarcia on 3/22/17.
 */
public class HeaderBagSteps extends DriverFactory {
    private HeaderBag headerBag = new HeaderBag(getDriver());

    @When("User clicks in bag")
    public void user_clicks_in_bag() {
        headerBag.clickBag();
    }

}
