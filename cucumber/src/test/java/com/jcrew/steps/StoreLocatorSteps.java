package com.jcrew.steps;


import com.jcrew.page.StoreLocatorPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

public class StoreLocatorSteps extends DriverFactory {

    private StoreLocatorPage storeLocator = new StoreLocatorPage(getDriver());

    @And("^User is in stores link$")
    public void user_is_in_stores_link() throws Throwable {
        assertTrue("Should have been in stores page", storeLocator.isFindByLocationPresent());
    }
}
