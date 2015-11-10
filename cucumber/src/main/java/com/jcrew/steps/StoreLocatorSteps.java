package com.jcrew.steps;


import com.jcrew.page.StoreLocatorPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

public class StoreLocatorSteps extends DriverFactory {

    private final StoreLocatorPage storeLocator = new StoreLocatorPage(getDriver());

    @And("^Verify user is on stores page$")
    public void verify_user_is_on_stores_page() throws Throwable {
        assertTrue("Should have been in stores page", storeLocator.isFindByLocationPresent());
    }
}
