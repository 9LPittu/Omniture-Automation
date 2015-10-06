package com.jcrew.steps;

import com.jcrew.page.ReturnsAndExchangesPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

/**
 * Created by 9hvenaga on 9/1/2015.
 */
public class ReturnsAndExchangesPageSteps extends DriverFactory {


    @And("^Verify user is on returns & exchanges page$")
    public void verify_user_is_on_returns_exchanges_page() throws Throwable {
        ReturnsAndExchangesPage returnsAndExchangesPage = new ReturnsAndExchangesPage(getDriver());
        assertTrue("User should be in returns and exchanges page", returnsAndExchangesPage.isReturnsAndExchangePage());
    }

}
