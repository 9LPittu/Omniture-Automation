package com.jcrew.steps;

import com.jcrew.page.RequestACatalogPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

public class RequestACatalogPageSteps extends DriverFactory {

    @And("^Verify user is on request a catalog page$")
    public void verify_user_is_on_request_a_catalog_page() throws Throwable {
        RequestACatalogPage catalogPage = new RequestACatalogPage(getDriver());
        assertTrue("User should have been in request a catalog page", catalogPage.isRequestACatalogPage());
    }
}
