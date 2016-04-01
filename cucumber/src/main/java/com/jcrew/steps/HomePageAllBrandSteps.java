package com.jcrew.steps;

import com.jcrew.page.HomePageAllBrands;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class HomePageAllBrandSteps extends DriverFactory {

    private final HomePageAllBrands homePageAllBrands = new HomePageAllBrands(getDriver());

    @And("^Verify page source contains ([^\"]*)$")
    public void validate_page_source_contains_given_var(String var) {
        assertTrue("page source should contain "+var+"", homePageAllBrands.isGivenVarPresentInSourceCode(var));
    }

    @And("^Get the ([^\"]*) value$")
    public void get_s_account_value(String var) {
       assertTrue("s_account should have a text value", !(homePageAllBrands.getSAccountValue().isEmpty()));
    }

    @And("^Validate the s_account value in production to be ([^\"]*)$")
    public void validate_s_account_value(String expected) {
        assertEquals("s_account value is not as expected", expected, homePageAllBrands.getSAccountValue());
    }

}