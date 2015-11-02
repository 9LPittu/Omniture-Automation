package com.jcrew.steps;

import com.jcrew.page.HelpStoreLocatorPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

public class HelpStoreLocatorPageSteps extends DriverFactory {

    @And("^Verify user is on help store locator page$")
    public void verify_user_is_on_help_store_locator_page() throws Throwable {
        HelpStoreLocatorPage storeLocatorPage = new HelpStoreLocatorPage(getDriver());
        assertTrue("User should have been in help store locator page", storeLocatorPage.isHelpStoreLocatorPage());
    }
}
